package com.exam;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import lombok.extern.java.Log;

// JUnit: 자바의 단위테스트 도구 (테스트 프레임워크)
//  	이클립스 등의 IDE는 JUnit과 통합되어 있음.
//  	JUnit의 테스트 메소드는 리턴형은 void, 매개변수는 없어야 함.
//  	@Test가 메소드에 선언되어야 함.

// 테스트케이스 클래스: 테스트를 수행하는 클래스

/*
Junit이 하나의 테스트 클래스를 가져와서 테스트를 수행하는 순서
1. 테스트 클래스에서 JUnit 관련 애노테이션이 붙은 메소드를 모두 찾는다.
2. @BeforeAll static 메소드가 있으면 최초 한번 실행한다.
3. 테스트 를래스의 오브젝트를 하나 만든다.
4. @BeforeEach 메소드가 있으면 실행한다.
5. @Test 메소드를 하나 호출하고 테스트 결과를 저장해둔다.
6. @AfterEach 메소드가 있으면 실행한다.
7. 나머지 @Test 메소드에 대해 앞의 3~6번을 반복한다.
8. @AfterAll static 메소드가 있으면 최후 한번 실행한다.
9. 모든 테스트의 결과를 종합해서 돌려준다.
*/

@Log
@DisplayName("테스트케이스 클래스")
public class MyTests {
	
	private Person person;
	
	@BeforeAll  // @BeforeAll는 static 메소드에만 붙일수 있음
	static void setUpBeforeAll() {
		log.info("@BeforeAll - static setUpBeforeAll() 호출됨");
	}
	
	@BeforeEach
	void setUp() {
		log.info("@BeforeEach - setUp() 호출됨");
		
		person = new Person("홍길동", 27, false);
	}
	
	@Test
	@DisplayName("Person객체 테스트")
	void testPerson() {
		log.info("@Test - testPerson() 호출됨");
		assertFalse(person.isMarried());
	}

	@Disabled
	@Test  // 테스트케이스 메소드
	void testHelloWorld() {
		log.info("@Test - testHelloWorld() 호출됨");
		
		// 객체일 경우 내용 비교
		assertEquals("Hello world!", Hello.helloWorld());
	}
	
	@Test
	void testGetNumArr() {
		log.info("@Test - testGetNumArr() 호출됨");
		
		int[] arr = { 10, 20, 30 };
		
		assertArrayEquals(arr, Hello.getNumArr());  // 배열객체 내용 비교
		assertNotSame(arr, Hello.getNumArr());  // 객체 주소 비교
	}
	
	@Test
	void testReceivePositiveNumber() {
		
		// 예외객체의 타입 비교
		Throwable exception = assertThrows(Exception.class, () -> Hello.receivePositiveNumber(-1));
		
//		Throwable exception = assertThrows(Exception.class, new Executable() {
//			
//			@Override
//			public void execute() throws Throwable {
//				Hello.receivePositiveNumber(-1);
//			}
//		});
		
		// 예외객체의 내용 비교
		assertEquals("음수는 받을 수 없습니다..", exception.getMessage());
	}
	
	@Test
	void testCalc() {
		log.info("@Test - testCalc() 호출됨");
		
		assertEquals(3, Calc.add(1, 2));
		assertEquals(5, Calc.sub(7, 2));
	}
	
	@AfterEach
	void tearDown() {
		log.info("@AfterEach - tearDown() 호출됨");
	}
	
	@AfterAll  // @AfterAll는 static 메소드에만 붙일수 있음
	static void tearDownAfterAll() {
		log.info("@AfterAll - static tearDownAfterAll() 호출됨");
	}
	
}
