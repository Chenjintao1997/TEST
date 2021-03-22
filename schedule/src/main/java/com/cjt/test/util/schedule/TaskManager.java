package com.cjt.test.util.schedule;

import com.google.common.collect.ImmutableMap;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskManager implements ApplicationContextAware {

    @Autowired
    Scheduler scheduler;

    ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    private Object registerBean(String beanId, Class<?> beanClass, Map<String, ?> beanProperties) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        if (beanProperties != null) {
            for (Map.Entry<String, ?> entry : beanProperties.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof StringBuilder) {
                    beanDefinitionBuilder.addPropertyReference(entry.getKey(), entry.getValue().toString());
                } else {
                    beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        defaultListableBeanFactory.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
        return defaultListableBeanFactory.getBean(beanId);
    }

    private void unregisterBean(String beanId) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        defaultListableBeanFactory.removeBeanDefinition(beanId);
    }

    public void addTask(String taskId, Task task) {
        Task taskBean = (Task) registerBean(taskId, task.getClass(), null);
        copyNonNullProperties(task, taskBean);
        JobDetail taskDetail = (JobDetail) registerBean(taskId + "Detail", MethodInvokingJobDetailFactoryBean.class,
                ImmutableMap.of("targetObject", new StringBuilder(taskId),
                        "targetMethod", "execute",
                        "concurrent", "false"));
        System.out.println("job信息----------->" + taskDetail.toString());
        try {
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExp())).build();
            System.out.println("trigger信息----------->" + trigger.toString());
            scheduler.scheduleJob(taskDetail, trigger);
        } catch (SchedulerException e) {
            throw new TaskException(e);
        }
    }

    public void removeTask(String taskId) {
        try {
            scheduler.deleteJob(new JobKey(taskId + "Detail"));
        } catch (SchedulerException e) {
            throw new TaskException(e);
        }
        unregisterBean(taskId + "Detail");
        unregisterBean(taskId);
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
