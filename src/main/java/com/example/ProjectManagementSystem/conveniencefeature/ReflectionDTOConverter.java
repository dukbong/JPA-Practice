package com.example.ProjectManagementSystem.conveniencefeature;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 * 실제로 사용하지 않는 것이 좋은 동적 DTO 생성 메소드
 * 1. 성능 문제 : 리플렉션은 정적 코드에 비해 상당히 느린 편이기 때문이다.
 * 2. 보안 위험 : 리플렉션은 너무 강력해서 위험을 초래할 수 있다.
 * 3. 유지 보수성 : 코드 변경 시 버그 발생 가능성이 높다.
 * 4. 디버깅 : 디버깅 하기가 정적 코드에 비해 많이 어렵다.
 */
public class ReflectionDTOConverter {
	
	public Object convertDto() {
		String dtoClassName = this.getClass().getSimpleName() + "DTO";
		try {
			Class<?> dtoClass = Class.forName("com.example.ProjectManagementSystem.dto." + dtoClassName);
			Method builderMethod = dtoClass.getMethod("builder");
			Object builder = builderMethod.invoke(null);
			Field[] targetFields = dtoClass.getDeclaredFields();
			for (Field targetField : targetFields) {
				// entityFields[i].setAccessible(true); // 1. 캡슐화를 깨트린다.
                String fieldName = targetField.getName(); 
                // Object fieldValue = entityFields[i].get(this); // 1. 캡슐화를 깨트린다.
                Method getMethod = this.getClass().getMethod(String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1))); // 캡슐화를 지키고 getter 메소드 사용
                Object fieldValue = getMethod.invoke(this);
                Method setterMethod = builder.getClass().getMethod(fieldName, targetField.getType());
                setterMethod.invoke(builder, fieldValue);
            }
			
			Method buildMethod = builder.getClass().getMethod("build");
            return buildMethod.invoke(builder);
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("DTO 클래스를 로드하는데 실패하였습니다.", e);
		} catch (NoSuchMethodException e) {
		    throw new RuntimeException("메소드를 로드하는데 실패하였습니다.", e);
		} catch (SecurityException e) {
		    throw new RuntimeException("보안 문제로 인해 메소드를 호출할 수 없습니다. 해당 메소드에 접근 권한이 없습니다.", e);
		} catch (IllegalAccessException e) {
		    throw new RuntimeException("메소드에 접근할 수 없습니다. 비공개 메소드이거나 접근 권한이 부족합니다.", e);
		} catch (IllegalArgumentException e) {
		    throw new RuntimeException("메소드 호출에 잘못된 인자가 전달되었습니다. 인자의 유형이나 값이 메소드 요구 사항과 맞지 않습니다.", e);
		} catch (InvocationTargetException e) {
		    Throwable cause = e.getCause();
		    throw new RuntimeException("메소드 실행 중 예외가 발생하였습니다. 메소드 자체의 문제가 있을 수 있습니다. 상세한 원인: " + cause.getMessage(), e);
		}
	}

}
