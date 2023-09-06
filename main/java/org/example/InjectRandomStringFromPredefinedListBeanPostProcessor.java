package org.example;

import org.example.InjectRandomStringFromList;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import java.util.Random;

public class InjectRandomStringFromPredefinedListBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        ReflectionUtils.doWithFields(beanClass, field -> {
            if (field.isAnnotationPresent(InjectRandomStringFromList.class)) {
                InjectRandomStringFromList annotation = field.getAnnotation(InjectRandomStringFromList.class);
                String[] predefinedStrings = annotation.value();

                if (predefinedStrings.length == 0) {
                    predefinedStrings = new String[]{"DefaultString"};
                }

                int randomIndex = new Random().nextInt(predefinedStrings.length);


                field.setAccessible(true);
                try {

                    if (field.getType().equals(String.class)) {
                        String randomString = annotation.prefix() + predefinedStrings[randomIndex];
                        field.set(bean, randomString);
                    } else {
                        throw new IllegalArgumentException("Поле, аннотированное с помощью @InjectRandomStringFromList, должно иметь тип String.");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Не удалось ввести в поле случайную строку", e);
                }
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
