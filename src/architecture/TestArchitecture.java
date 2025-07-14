package architecture;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import components.Memory;

public class TestArchitecture {
	
	//uncomment the anotation below to run the architecture showing components status
	//@Test
	public void testShowComponentes() {

		//a complete test (for visual purposes only).
		//a single code as follows
//		ldi 2
//		store 40
//		ldi -4
//		point:
//		store 41  //mem[41]=-4 (then -3, -2, -1, 0)
//		read 40
//		add 40    //mem[40] + mem[40]
//		store 40  //result must be in 40
//		read 41
//		inc
//		jn point
//		end
		
		Architecture arch = new Architecture(true);
		arch.getMemory().getDataList()[0]=7;
		arch.getMemory().getDataList()[1]=2;
		arch.getMemory().getDataList()[2]=6;
		arch.getMemory().getDataList()[3]=40;
		arch.getMemory().getDataList()[4]=7;
		arch.getMemory().getDataList()[5]=-4;
		arch.getMemory().getDataList()[6]=6;
		arch.getMemory().getDataList()[7]=41;
		arch.getMemory().getDataList()[8]=5;
		arch.getMemory().getDataList()[9]=40;
		arch.getMemory().getDataList()[10]=0;
		arch.getMemory().getDataList()[11]=40;
		arch.getMemory().getDataList()[12]=6;
		arch.getMemory().getDataList()[13]=40;
		arch.getMemory().getDataList()[14]=5;
		arch.getMemory().getDataList()[15]=41;
		arch.getMemory().getDataList()[16]=8;
		arch.getMemory().getDataList()[17]=4;
		arch.getMemory().getDataList()[18]=6;
		arch.getMemory().getDataList()[19]=-1;
		arch.getMemory().getDataList()[40]=0;
		arch.getMemory().getDataList()[41]=0;
		//now the program and the variables are stored. we can run
		arch.controlUnitEexec();
	}
	
	@Test
	// public void testAdd() {
	// 	Architecture arch = new Architecture();
	// 	//storing the number 5 in position 40
	// 	arch.getExtbus1().put(40);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(5);
	// 	arch.getMemory().store();
	// 	//moreover, the number 40 must be in the position next to PC. (to perform add 40)
	// 	//in this test, PC will point to 10 and the 40 will be in the position 11
	// 	arch.getExtbus1().put(11);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(40); //40 is in position 11
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(10);
	// 	arch.getPC().store();      //PC points to position 10
		
	// 	//storing the number 8 in the RPG
	// 	arch.getIntbus1().put(8);
	// 	arch.getRPG0().internalStore();
		
	// 	//now we can perform the add method. 
	// 	//we will add the number 5 (stored in the 40th position in the memory) 
	// 	//with the number 8 (already stored in the rgp)
	// 	//result must be into rpg
	// 	//pc must be two positions ahead the original one
	// 	arch.add();
	// 	arch.getRPG0().internalRead();
	// 	//the bus must contains the number 13
	// 	assertEquals(13, arch.getIntbus1().get());
	// 	//the flags bits 0 and 1 must be 0
	// 	assertEquals(0, arch.getFlags().getBit(0));
	// 	assertEquals(0, arch.getFlags().getBit(1));
	// 	//PC must be pointing to 12
	// 	arch.getPC().read();
	// 	assertEquals(12, arch.getExtbus1().get());

	// }
	
	public void testAddRegReg() {
	    Architecture arch = new Architecture();
	    
	    //Storing the number 0 in the memory, in position 11
	    arch.getMemory().getDataList()[11] = 0;
	    //Storing the number 1 in the memory, in position 12
	    arch.getMemory().getDataList()[12] = 1;
	    
	    // Sets PC's value to the position 10 of the memory
	    arch.getExtbus1().put(10);
	    arch.getPC().store();

	    // Update the values of the registers
	    arch.getIntbus1().put(10);
	    arch.getRPG0().internalStore(); // RPG0 (regA) receives 10
	    arch.getIntbus1().put(5);
	    arch.getRPG1().internalStore(); // RPG1 (regB) receives 5

	    // Executes the addRegReg() instruction (addition operation)
	    arch.addRegReg();

	    // Checks if the result is correct: RPG1 (regB) should contain the sum: 15 (10 + 5)
	    arch.getRPG1().internalRead();
	    assertEquals(15, arch.getIntbus1().get());
	    
	    // Checks if RPG0 (regA) remains unchanged
	    arch.getRPG0().internalRead();
	    assertEquals(10, arch.getIntbus1().get());

	    // Testing if PC points to 3 positions after the original
	    // PC was pointing to 10; now it must be pointing to 13
	    arch.getPC().read();
	    assertEquals(13, arch.getExtbus1().get());

		// The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testAddMemReg() {
	    Architecture arch = new Architecture();
	    
	    //Stores the number 35 into the memory in the position 31
	    arch.getMemory().getDataList()[31] = 35; 
	    //Stores the number 0 into the memory in the position 32
	    arch.getMemory().getDataList()[32] = 0;
	    //Stores the number 3 into the memory in the position 35
	    arch.getMemory().getDataList()[35] = 3;
	    
	    //set the pc Value to 30
	    arch.getExtbus1().put(30);
	    arch.getPC().store();
	    
	    //Clears the extBus
	    arch.getExtbus1().put(0);
	    
	    //Setting the register 0 with the value 5
	    arch.getIntbus1().put(5);
	    arch.getRegistersList().get(0).internalStore();
	    
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Now the addMemReg command can be executed 
	    //In the end of the execution the value in Reg0 must be 8
	    arch.addMemReg();
	    arch.getRPG0().internalRead();
	    assertEquals(8, arch.getIntbus1().get());
	    
	    //PC must be pointing to 33
	    arch.getPC().read();
	    assertEquals(33, arch.getExtbus1().get());
	    
	    //The value of the memory in position 31 must be 35
	    arch.getExtbus1().put(31);
	    arch.getMemory().read();
	    assertEquals(35, arch.getExtbus1().get());
	    //The value of the memory in position 32 must be 0
	    arch.getExtbus1().put(32);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
	    //The value of the memory in position 35 must be 3
	    arch.getExtbus1().put(35);
	    arch.getMemory().read();
	    assertEquals(3, arch.getExtbus1().get());

		//The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testAddRegMem() {
		
		Architecture arch = new Architecture();
		
		//Stores the number 1 in the position 21 of the memory
	    arch.getMemory().getDataList()[21] = 1;
	    //Stores the number 26 in the position 22 of the memory
	    arch.getMemory().getDataList()[22] = 26;
	    //Stores the number 30 in the position 26 of the memory
	    arch.getMemory().getDataList()[26] = 30;
	    
	    //Stores the value 25 into the Register 1
	    arch.getIntbus1().put(25);
	    arch.getRPG1().internalStore();
	    
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Set PC's value as 20
	    arch.getExtbus1().put(20);
	    arch.getPC().store();
	    
	    //Clears the external bus
	    arch.getExtbus1().put(0);
	    
	    //Now the subRegMem command can be executed
	    arch.addRegMem();
	    
	    //In the end of the execution, the position 26 of the memory must store -5
	    arch.getExtbus1().put(26);
	    arch.getMemory().read();
	    assertEquals(55, arch.getExtbus1().get());
	    
	    //The value stored into the position 21 of the memory must be 1
	    arch.getExtbus1().put(21);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    
	    //The value stored into the position 22 of the memory must be 26
	    arch.getExtbus1().put(22);
	    arch.getMemory().read();
	    assertEquals(26, arch.getExtbus1().get());
	    
	    //The value stored into the Register 1 must be 25
	    arch.getRPG1().internalRead();
	    assertEquals(25, arch.getIntbus1().get());
	    
	    //PC must be pointing to the position 23
	    arch.getPC().read();
	    assertEquals(23, arch.getExtbus1().get());
	    
	    //The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	}

	@Test
	public void testImulMemReg(){
		Architecture arch = new Architecture();
		/**
		 * Multiplicaremos 5 * 2 
		 * Usando:
		 * Mem (posicao 30) = 2
		 * Reg2 = 5
		 * 
		 * No final, Reg2 = 5  e Mem (posicao 30) = 10
		 */
		arch.getMemory().getDataList()[2] = 5; //Valor armazenado na mem
		arch.getMemory().getDataList()[29] = 2; // Valor armazenado na mem
		arch.getMemory().getDataList()[30] = 2; //ID do Reg2
		arch.getMemory().getDataList()[31] = -1; //Valor armazenado na mem

		//PC aponta para 28
		arch.getExtbus1().put(28);
		arch.getPC().store();
		//Limpa o extBus
		arch.getExtbus1().put(0);

		//Setamos RPG2 com o valor 5
		arch.getIntbus2().put(5);
		arch.getRPG2().store();
		//Limpa o bus
		arch.getIntbus2().put(0);

		//Executamos o programa
		arch.imulMemReg();

		arch.controlUnitEexec();

		//No fim, PC deve ser igual a 31
		arch.getPC().read();
		assertEquals(31, arch.getExtbus1().get());

		//Na memoria, a posicao 29 deve ser igual a 2 
		// e a posição 30 deve ser 1
		arch.getExtbus1().put(29);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		arch.getExtbus1().put(30);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		//Agora, verificamos os RPGS, todos exceto o 2 devem ser 0
		//RPG2 deve ser 5
		arch.getRPG0().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG1().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG2().read();
		assertEquals(25, arch.getIntbus2().get());

		arch.getRPG3().read();
		assertEquals(0, arch.getIntbus2().get());
	}
	
	@Test
	public void testImulRegMem(){
		Architecture arch = new Architecture();
		/**
		 * Multiplicaremos 5 * 5
		 * Usando:
		 * Mem (posicao 2) = 25
		 * Reg2 = 5
		 * Mem (posicao 30) = 2
		 * 
		 * No final, Reg2 = 5  e Mem (posicao 30) = 2
		 */
		arch.getMemory().getDataList()[2] = 5; //Valor armazenado na mem
		arch.getMemory().getDataList()[29] = 2; //ID do Reg2 
		arch.getMemory().getDataList()[30] = 2; //Valor armazenado na mem
		arch.getMemory().getDataList()[31] = -1; //Valor armazenado na mem
		//PC aponta para 28
		arch.getExtbus1().put(28);
		arch.getPC().store();
		//Limpa o extBus
		arch.getExtbus1().put(0);

		//Setamos RPG2 com o valor 5
		arch.getIntbus2().put(5);
		arch.getRPG2().store();
		//Limpa o bus
		arch.getIntbus2().put(0);

		//Executamos o programa
		arch.imulRegMem();
		
		arch.controlUnitEexec();

		//No fim, PC deve ser igual a 31
		arch.getPC().read();
		assertEquals(31, arch.getExtbus1().get());

		//Na memoria, a posicao 29 deve ser igual a 2 
		// e a posição 30 deve ser 10
		arch.getExtbus1().put(29);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		arch.getExtbus1().put(30);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		//Agora, verificamos os RPGS, todos exceto o 2 devem ser 0
		//RPG2 deve ser 5
		arch.getRPG0().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG1().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG3().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG2().read();
		assertEquals(5, arch.getIntbus2().get());
		assertEquals(25, arch.getMemory().getDataList()[2]);
		
		/**
		 * Multiplicaremos 9 * 10
		 */
		arch.getMemory().getDataList()[2] = 10; //Valor armazenado na mem
		arch.getMemory().getDataList()[29] = 2; //ID do Reg2 
		arch.getMemory().getDataList()[30] = 2; //Valor armazenado na mem
		arch.getMemory().getDataList()[31] = -1; //Valor armazenado na mem
		//PC aponta para 28
		arch.getExtbus1().put(28);
		arch.getPC().store();
		//Limpa o extBus
		arch.getExtbus1().put(0);

		//Setamos RPG2 com o valor 9
		arch.getIntbus2().put(9);
		arch.getRPG2().store();
		//Limpa o bus
		arch.getIntbus2().put(0);

		//Executamos o programa
		arch.imulRegMem();
		
		arch.controlUnitEexec();

		//No fim, PC deve ser igual a 31
		arch.getPC().read();
		assertEquals(31, arch.getExtbus1().get());

		//Na memoria, a posicao 29 deve ser igual a 2 
		// e a posição 30 deve ser 10
		arch.getExtbus1().put(29);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		arch.getExtbus1().put(30);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		//Agora, verificamos os RPGS, todos exceto o 2 devem ser 0
		//RPG2 deve ser 5
		arch.getRPG0().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG1().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG3().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG2().read();
		assertEquals(9, arch.getIntbus2().get());
		assertEquals(90, arch.getMemory().getDataList()[2]);
	}
	
	@Test
	public void testImulRegReg(){
		Architecture arch = new Architecture();
		/*
		 * Multiplicaremos 5 * 2 
		 * Usando:
		 * Reg2 = 5
		 * Reg3 = 2
		 * 
		 * No final, Reg2 = 5  e Reg3 = 10
		 */
		arch.getMemory().getDataList()[29] = 2; //ID do Reg2 
		arch.getMemory().getDataList()[30] = 3; //ID do Reg3
		arch.getMemory().getDataList()[31] = -1; //ID do Reg3

		
		//PC aponta para 28
		arch.getExtbus1().put(28);
		arch.getPC().store();
		
		//Limpa o extBus
		arch.getExtbus1().put(0);

		//Setamos RPG2 com o valor 5
		arch.getIntbus2().put(5);
		arch.getRPG2().store();
		//Limpa o bus
		arch.getIntbus2().put(0);
		
		//Setamos RPG3 com o valor 2
		arch.getIntbus2().put(2);
		arch.getRPG3().store();
		//Limpa o bus
		arch.getIntbus2().put(0);

		//Executamos o programa
		arch.imulRegReg();
		arch.controlUnitEexec();

		//No fim, PC deve ser igual a 31
		arch.getPC().read();
		assertEquals(31, arch.getExtbus1().get());

		//Na memoria, a posicao 29 deve ser igual a 2 
		// e a posição 30 deve ser 10
		arch.getExtbus1().put(29);
		arch.getMemory().read();
		assertEquals(2, arch.getExtbus1().get());

		arch.getExtbus1().put(30);
		arch.getMemory().read();
		assertEquals(3, arch.getExtbus1().get());

		//Agora, verificamos os RPGS, todos exceto o 2 devem ser 0
		//RPG2 deve ser 5
		arch.getRPG0().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG1().read();
		assertEquals(0, arch.getIntbus2().get());

		arch.getRPG2().read();
		assertEquals(5, arch.getIntbus2().get());
		
		arch.getRPG3().read();
		assertEquals(10, arch.getIntbus2().get());		
	}
	
	@Test
	public void testaddImmReg() {
	    Architecture arch = new Architecture();
		//Stores the number 100 into the memory in the position 70
	    arch.getMemory().getDataList()[70] = 100; 

		//Stores the number register into the memory in the position 71
	    arch.getMemory().getDataList()[71] = 1;
	    
	    //set the pc Value to 69
	    arch.getExtbus1().put(69);
	    arch.getPC().store();
	    
	    arch.getExtbus1().put(0);

	    arch.getIntbus1().put(5);
	    arch.getRegistersList().get(1).internalStore();
	    
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Now the addMemReg command can be executed 
	    //In the end of the execution the value in Reg0 must be 8
	    arch.addImmReg();
	    arch.getRPG1().internalRead();
	    assertEquals(105, arch.getIntbus1().get());
	    
	    //PC must be pointing to 33
	    arch.getPC().read();
	    assertEquals(72, arch.getExtbus1().get());
	    
	    //The value of the memory in position 31 must be 35
	    arch.getExtbus1().put(70);
	    arch.getMemory().read();
	    assertEquals(100, arch.getExtbus1().get());

		//The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	}
	
	//@Test
	// public void testSub() {
	// 	Architecture arch = new Architecture();
		
	// 	/*************************
	// 	 * first test: 5 (rpg) - 8 (mem-40) = -3 (rpg)
	// 	 ***********************************************/
		
	// 	//storing the number 8 in the memory, in position 40
	// 	arch.getExtbus1().put(40);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(8);
	// 	arch.getMemory().store();
	// 	//storing the number 5 in the external bus
	// 	arch.getIntbus1().put(5);
	// 	//moving this number 5 into the rpg
	// 	arch.getRPG0().internalStore();
		
	// 	//moreover, the number 40 must be in the position next to PC. (to perform sub 40)
	// 	//in this test, PC will point to 10 and the 40 will be in the position 11
	// 	arch.getExtbus1().put(11);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(40); //40 is in position 11
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(10);
	// 	arch.getPC().store();      //PC points to position 10
		
	// 	//now we can perform the sub method. 
	// 	//we will sub, from the number 5 (stored in the rpg) 
	// 	//the number 8 (stored in the memory, position 40)
	// 	//result must be into rpg
	// 	arch.sub();
	// 	arch.getRPG0().internalRead();
	// 	//the bus must contains the number -3
	// 	assertEquals(-3, arch.getIntbus1().get());
		
	// 	//flags bits must be 0 (bit zero) and 1 (bit negative)
	// 	assertEquals(0, arch.getFlags().getBit(0));
	// 	assertEquals(1, arch.getFlags().getBit(1));
		
		
	// 	//PC must be pointing to 12
	// 	arch.getPC().read();
	// 	assertEquals(12, arch.getExtbus1().get());

	// 	/*************************
	// 	 * second test: 5 (rpg) - 5 (mem-50) = 0 (rpg)
	// 	 ***********************************************/
		
	// 	//storing the number 5 in the memory, in position 50
	// 	arch.getExtbus1().put(50);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(5);
	// 	arch.getMemory().store();
	// 	//storing the number 5 in the internal bus 1
	// 	arch.getIntbus1().put(5);
	// 	//moving this number 5 into the rpg
	// 	arch.getRPG0().internalStore();
		
	// 	//moreover, the number 50 must be in the position next to PC. (to perform sub 50)
	// 	//in this test, PC will point to 12 and the 50 will be in the position 13
	// 	arch.getExtbus1().put(13);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(50); //50 is in position 13
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(12);
	// 	arch.getPC().store();      //PC points to position 12
		
		
	// 	//now we can perform the sub method. 
	// 	//we will sub, from the number 5 (stored in the rpg) 
	// 	//the number 5 (already stored in the memory, position 50)
	// 	//result must be into rpg
	// 	arch.sub();
	// 	arch.getRPG0().internalRead();
	// 	//the bus must contains the number 0
	// 	assertEquals(0, arch.getIntbus1().get());
		
	// 	//flags bits must be 1 (bit zero) and 0 (bit negative)
	// 	assertEquals(1, arch.getFlags().getBit(0));
	// 	assertEquals(0, arch.getFlags().getBit(1));
		
	// 	//PC must be pointing to 14
	// 	arch.getPC().read();
	// 	assertEquals(14, arch.getExtbus1().get());
		
	// 	/*************************
	// 	 * third test: 5 (rpg) - 3 (mem-60) = 2 (rpg)
	// 	 ***********************************************/
		
	// 	//storing the number 3 in the memory, in position 60
	// 	arch.getExtbus1().put(60);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(3);
	// 	arch.getMemory().store();
	// 	//storing the number 5 in the internal bus 1
	// 	arch.getIntbus1().put(5);
	// 	//moving this number 5 into the rpg
	// 	arch.getRPG0().internalStore();
		
	// 	//moreover, the number 60 must be in the position next to PC. (to perform sub 60)
	// 	//in this test, PC will point to 14 and the 60 will be in the position 15
	// 	arch.getExtbus1().put(15);
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(60); //60 is in position 15
	// 	arch.getMemory().store();
	// 	arch.getExtbus1().put(14);
	// 	arch.getPC().store();      //PC points to position 14
		
		
	// 	//now we can perform the sub method. 
	// 	//we will sub, from the number 5 (stored in the rpg) 
	// 	//the number 3 (already stored in the memory, position 60)
	// 	//result must be into rpg
	// 	arch.sub();
	// 	arch.getRPG0().internalRead();
	// 	//the bus must contains the number 2
	// 	assertEquals(2, arch.getIntbus1().get());
		
	// 	//flags bits must be 0 (bit zero) and 0 (bit negative)
	// 	assertEquals(0, arch.getFlags().getBit(0));
	// 	assertEquals(0, arch.getFlags().getBit(1));
		
	// 	//PC must be pointing to 16
	// 	arch.getPC().read();
	// 	assertEquals(16, arch.getExtbus1().get());
	// }
	
	@Test
	public void testSubRegReg() {
		Architecture arch = new Architecture();
		/**
		 * SUB REG0 REG1
		 * SUB 20   5
		 */
		// Stores the number 0 in the position 10 of the memory
		arch.getMemory().getDataList()[10] = 0;
		// Stores the number 1 in the position 11 of the memory
		arch.getMemory().getDataList()[11] = 1;
		
		// Stores the value 20 into the Register 0
		arch.getIntbus1().put(20);
		arch.getRPG0().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
		
		// Stores the value 5 into the Register 1
		arch.getIntbus1().put(5);
		arch.getRPG1().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
		// Making PC points to position 9
		arch.getExtbus1().put(9);
		arch.getPC().store();
		
		// Executing the command sub Reg0 Reg1.
		arch.subRegReg();
		
		// Clears the external bus
	    arch.getExtbus1().put(0);
		
		// The value stored into the position 10 of the memory must be 0
	    arch.getExtbus1().put(10);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
		
	    // The value stored into the position 11 of the memory must be 1
	    arch.getExtbus1().put(11);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
		
		// The value stored into the Register 0 must be 20
		arch.getRegistersList().get(0).internalRead();
		assertEquals(20, arch.getIntbus1().get());
		
		// The value stored into the Register 1 must be 15
	    arch.getRegistersList().get(1).internalRead();
	    assertEquals(15, arch.getIntbus1().get());
		
		// Testing if PC points to 3 positions after the original
		// PC was pointing to 9; now it must be pointing to 12
		arch.getPC().read();assertEquals(12, arch.getExtbus1().get());
		
		// The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));

		/**
		 * SUB REG 0 REG 1
		 * SUB 20 20
		 */
		// Stores the number 0 in the position 10 of the memory
		arch.getMemory().getDataList()[10] = 0;
		// Stores the number 1 in the position 11 of the memory
		arch.getMemory().getDataList()[11] = 1;
		
		// Stores the value 20 into the Register 0
		arch.getIntbus1().put(20);
		arch.getRPG0().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
		
		// Stores the value 20 into the Register 1
		arch.getIntbus1().put(20);
		arch.getRPG1().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
		// Making PC points to position 9
		arch.getExtbus1().put(9);
		arch.getPC().store();
		
		// Executing the command sub Reg0 Reg1.
		arch.subRegReg();
		
		// Clears the external bus
	    arch.getExtbus1().put(0);
		
		// The value stored into the position 10 of the memory must be 0
	    arch.getExtbus1().put(10);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
		
	    // The value stored into the position 11 of the memory must be 1
	    arch.getExtbus1().put(11);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
		
		// The value stored into the Register 0 must be 20
		arch.getRegistersList().get(0).internalRead();
		assertEquals(20, arch.getIntbus1().get());
		
		// The value stored into the Register 1 must be 20
	    arch.getRegistersList().get(1).internalRead();
	    assertEquals(0, arch.getIntbus1().get());
		
		// Testing if PC points to 3 positions after the original
		// PC was pointing to 9; now it must be pointing to 12
		arch.getPC().read();assertEquals(12, arch.getExtbus1().get());
		
		// The flag bit zero must be 1, and bit negative must be 0
	    assertEquals(1, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	    
	    /**
		 * SUB REG 0 REG 1
		 * SUB 5 20
		 */
		// Stores the number 0 in the position 10 of the memory
		arch.getMemory().getDataList()[10] = 0;
		// Stores the number 1 in the position 11 of the memory
		arch.getMemory().getDataList()[11] = 1;
		
		// Stores the value 5 into the Register 0
		arch.getIntbus1().put(5);
		arch.getRPG0().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
		
		// Stores the value 20 into the Register 1
		arch.getIntbus1().put(20);
		arch.getRPG1().internalStore();
		
		// Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
		// Making PC points to position 9
		arch.getExtbus1().put(9);
		arch.getPC().store();
		
		// Executing the command sub Reg0 Reg1.
		arch.subRegReg();
		
		// Clears the external bus
	    arch.getExtbus1().put(0);
		
		// The value stored into the position 10 of the memory must be 0
	    arch.getExtbus1().put(10);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
		
	    // The value stored into the position 11 of the memory must be 1
	    arch.getExtbus1().put(11);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
		
		// The value stored into the Register 0 must be 5
		arch.getRegistersList().get(0).internalRead();
		assertEquals(5, arch.getIntbus1().get());
		
		// The value stored into the Register 1 must be -15
	    arch.getRegistersList().get(1).internalRead();
	    assertEquals(-15, arch.getIntbus1().get());
		
		// Testing if PC points to 3 positions after the original
		// PC was pointing to 9; now it must be pointing to 12
		arch.getPC().read();assertEquals(12, arch.getExtbus1().get());
		
		// The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(1, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testSubMemReg() {
	    Architecture arch = new Architecture();

	    // Updates the values in memory 
	    arch.getMemory().getDataList()[21] = 25; // Memory position 20 contains 25
	    arch.getMemory().getDataList()[22] = 1; // Memory position 21 contains 1
	    arch.getMemory().getDataList()[25] = 30; // Memory position 25 contains 30
	    
	    // Register RPG1 receives 15
	    arch.getIntbus1().put(15); 
	    arch.getRPG1().internalStore();
	    
	    // Sets PC's value to the position 25 of the memory
	    arch.getExtbus1().put(20);
	    arch.getPC().store();

	    // Executes the subMemReg() instruction (subtraction operation)
	    arch.subMemReg();

	    // Checks if the result is correct: RPG1 should be 15 (30 - 15)
	    arch.getRPG1().internalRead();
	    assertEquals(15, arch.getIntbus1().get());

	    // Testing if PC points to 3 positions after the original
	    // PC was pointing to 20; now it must be pointing to 23
	    arch.getPC().read();
	    assertEquals(23, arch.getExtbus1().get());
	    
	    //The value of the memory in position 21 must be 25
	    arch.getExtbus1().put(21);
	    arch.getMemory().read();
	    assertEquals(25, arch.getExtbus1().get());
	    //The value of the memory in position 22 must be 1
	    arch.getExtbus1().put(22);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    //The value of the memory in position 25 must be 30
	    arch.getExtbus1().put(25);
	    arch.getMemory().read();
	    assertEquals(30, arch.getExtbus1().get());

		// The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	    
	    /**
	     * Nesse teste, o resultado da operação deve ser negativo
	     * 
	     * */
	    // Updates the values in memory 
	    arch.getMemory().getDataList()[21] = 25; // Memory position 20 contains 25
	    arch.getMemory().getDataList()[22] = 1; // Memory position 21 contains 1
	    arch.getMemory().getDataList()[25] = 10; // Memory position 25 contains 10
	    
	    // Register RPG1 receives 15
	    arch.getIntbus1().put(15); 
	    arch.getRPG1().internalStore();
	    
	    // Sets PC's value to the position 25 of the memory
	    arch.getExtbus1().put(20);
	    arch.getPC().store();

	    // Executes the subMemReg() instruction (subtraction operation)
	    arch.subMemReg();

	    // Checks if the result is correct: RPG1 should be -5 (10 - 15)
	    arch.getRPG1().internalRead();
	    assertEquals(-5, arch.getIntbus1().get());

	    // Testing if PC points to 3 positions after the original
	    // PC was pointing to 20; now it must be pointing to 23
	    arch.getPC().read();
	    assertEquals(23, arch.getExtbus1().get());
	    
	    //The value of the memory in position 21 must be 25
	    arch.getExtbus1().put(21);
	    arch.getMemory().read();
	    assertEquals(25, arch.getExtbus1().get());
	    //The value of the memory in position 22 must be 1
	    arch.getExtbus1().put(22);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    //The value of the memory in position 25 must be 10
	    arch.getExtbus1().put(25);
	    arch.getMemory().read();
	    assertEquals(10, arch.getExtbus1().get());

		// The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(1, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testSubRegMem() {
	    Architecture arch = new Architecture();
	    
	    //Stores the number 1 in the position 21 of the memory
	    arch.getMemory().getDataList()[21] = 1;
	    //Stores the number 26 in the position 22 of the memory
	    arch.getMemory().getDataList()[22] = 26;
	    //Stores the number 30 in the position 26 of the memory
	    arch.getMemory().getDataList()[26] = 30;
	    
	    //Stores the value 25 into the Register 1
	    arch.getIntbus1().put(25);
	    arch.getRPG1().internalStore();
	    
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Set PC's value as 20
	    arch.getExtbus1().put(20);
	    arch.getPC().store();
	    
	    //Clears the external bus
	    arch.getExtbus1().put(0);
	    
	    //Now the subRegMem command can be executed
	    arch.subRegMem();
	    
	    //In the end of the execution, the position 26 of the memory must store -5
	    arch.getExtbus1().put(26);
	    arch.getMemory().read();
	    assertEquals(-5, arch.getExtbus1().get());
	    
	    //The value stored into the position 21 of the memory must be 1
	    arch.getExtbus1().put(21);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    
	    //The value stored into the position 22 of the memory must be 26
	    arch.getExtbus1().put(22);
	    arch.getMemory().read();
	    assertEquals(26, arch.getExtbus1().get());
	    
	    //The value stored into the Register 1 must be 25
	    arch.getRPG1().internalRead();
	    assertEquals(25, arch.getIntbus1().get());
	    
	    //PC must be pointing to the position 23
	    arch.getPC().read();
	    assertEquals(23, arch.getExtbus1().get());
	    
	    //The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(1, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testSubImmReg() {
	    Architecture arch = new Architecture();
		//Stores the number 100 into the memory in the position 70
	    arch.getMemory().getDataList()[70] = 100; 

		//Stores the number register into the memory in the position 71
	    arch.getMemory().getDataList()[71] = 1;
	    
	    //set the pc Value to 69
	    arch.getExtbus1().put(69);
	    arch.getPC().store();
	    
	    arch.getExtbus1().put(0);
	    
	    //Stores the value 25 into the Register 1
	    arch.getIntbus1().put(150);
	    arch.getRPG1().internalStore();

	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Now the addMemReg command can be executed 
	    //In the end of the execution the value in Reg0 must be 8
	    arch.subImmReg();
	    arch.getRPG1().internalRead();
	    assertEquals(-50, arch.getIntbus1().get());
	    
	    //PC must be pointing to 72
	    arch.getPC().read();
	    assertEquals(72, arch.getExtbus1().get());
	    
	    //The value of the memory in position 70 must be 100
	    arch.getExtbus1().put(70);
	    arch.getMemory().read();
	    assertEquals(100, arch.getExtbus1().get());
	    
	    //The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(1, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testMoveMemReg() {
		Architecture arch = new Architecture();

		//storing the number 1 in the memory, in position 31
		arch.getMemory().getDataList()[61]=80;
		//storing the number 0 in the memory, in position 32
		arch.getMemory().getDataList()[62]=2;
		//making PC points to position 30
		arch.getExtbus1().put(60);
		arch.getPC().store();
		
		
		//now setting the registers values
		arch.getMemory().getDataList()[80]=25;
		arch.getIntbus1().put(40);
		arch.getRegistersList().get(2).internalStore(); //RPG1 has 99
		
		//executing the command move REG1 REG0.
		arch.moveMemReg();
		
		//testing if both REG1 and REG0 store the same value: 99
		arch.getExtbus1().put(61);
		arch.getMemory().read();
		arch.getMemory().read();
		assertEquals(25, arch.getExtbus1().get());
		arch.getRegistersList().get(2).internalRead();
		assertEquals(25, arch.getIntbus1().get());
		
		//Testing if PC points to 3 positions after the original
		//PC was pointing to 30; now it must be pointing to 33
		arch.getPC().read();assertEquals(63, arch.getExtbus1().get());
	}
	
	@Test
	public void testMoveRegMem() {
		Architecture arch = new Architecture();
		
		//Stores the number 1 in the position 21 of the memory
	    arch.getMemory().getDataList()[21] = 1;
	    //Stores the number 26 in the position 22 of the memory
	    arch.getMemory().getDataList()[22] = 26;
	    //Stores the number 30 in the position 26 of the memory
	    arch.getMemory().getDataList()[26] = 30;
	    
	    //Stores the value 25 into the Register 1
	    arch.getIntbus1().put(25);
	    arch.getRPG1().internalStore();
		
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Set PC's value as 20
	    arch.getExtbus1().put(20);
	    arch.getPC().store();
	    
	    // Clears the external bus
	    arch.getExtbus1().put(0);
	    
	    // Now the moveRegMem command can be executed
	    arch.moveRegMem();
	    
	    //The value stored into the position 21 of the memory must be 1
	    arch.getExtbus1().put(21);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    
	    //The value stored into the position 22 of the memory must be 26
	    arch.getExtbus1().put(22);
	    arch.getMemory().read();
	    assertEquals(26, arch.getExtbus1().get());
		
	    // In the end of the execution, the position 26 of the memory must store -5
	    arch.getExtbus1().put(26);
	    arch.getMemory().read();
	    assertEquals(25, arch.getExtbus1().get());
	    
	    // The value stored into the Register 1 must be 25
	    arch.getRPG1().internalRead();
	    assertEquals(25, arch.getIntbus1().get());
		
		//Testing if PC points to 3 positions after the original
		//PC was pointing to 20; now it must be pointing to 23
		arch.getPC().read();
		assertEquals(23, arch.getExtbus1().get());
		
		// The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));		
	}
	
	@Test
	public void testMoveRegReg() {
	    Architecture arch = new Architecture();
	       
	    //Storing the number 1 in the memory, in position 36
		arch.getMemory().getDataList()[36] = 1;
		//Storing the number 0 in the memory, in position 37
		arch.getMemory().getDataList()[37] = 0;
		
		// Sets PC's value to the position 35 of the memory
	    arch.getExtbus1().put(35);
	    arch.getPC().store();

	    // Updates the values of the registers
	    arch.getIntbus1().put(10); 
	    arch.getRPG0().internalStore(); // RPG0 receives 10
	    arch.getIntbus1().put(45); 
	    arch.getRPG1().internalStore(); // RPG1 receives 45
	    
	    // Executes the moveRegReg() instruction
	    arch.moveRegReg();		
	    
	    // Checks if RPG1 received the value from RPG0, they should have the same value (45)
	    arch.getRPG0().internalRead();
	    assertEquals(45, arch.getIntbus1().get());
	    arch.getRPG1().internalRead();
	    assertEquals(45, arch.getIntbus1().get());

	    // Testing if PC points to 3 positions after the original
	    // PC was pointing to 35; now it must be pointing to 38
	    arch.getPC().read();
	    assertEquals(38, arch.getExtbus1().get());
	}
	
	@Test
	public void testMoveImmReg() {
	    Architecture arch = new Architecture();
	    
	    //Stores the value 105 in the position 16 of the memory
	    arch.getMemory().getDataList()[16] = 105;
	    //Stores the value 2 in the position 17 of the memory
	    arch.getMemory().getDataList()[17] = 2;
	    
	    //Sets PC's value to the position 15 of the memory
	    arch.getExtbus1().put(15);
	    arch.getPC().store();
	    
	    //Clears external bus
	    arch.getExtbus1().put(0);
	    
	    //Set Reg2 value as 15
	    arch.getIntbus1().put(15);
	    arch.getRPG2().internalStore();
	    
	    //Clears internal bus 1
	    arch.getIntbus1().put(0);
	    //Verifies if RPG2 stores the correct value
	    arch.getRPG2().internalRead();
	    assertEquals(15, arch.getIntbus1().get());
	    //Clears internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Now the command can be executed
	    arch.moveImmReg();
	    
	    //The position 16 of the memory must store 105
	    arch.getExtbus1().put(16);
	    arch.getMemory().read();
	    assertEquals(105, arch.getExtbus1().get());
	    //The position 17 of the memory must store 2
	    arch.getExtbus1().put(17);
	    arch.getMemory().read();		
	    assertEquals(2, arch.getExtbus1().get());
	    
	    //The value stored into RPG2 must be 105
	    arch.getRPG2().internalRead();
	    assertEquals(105, arch.getIntbus1().get());
	    
	    //The value stored into PC must be 18
	    arch.getPC().read();
	    assertEquals(18, arch.getExtbus1().get());
	}
	
	// @Test
	// public void testInc() {
	// 	Architecture arch = new Architecture();
	// 	//storing the number 10 in RPG
	// 	arch.getIntbus1().put(10);
	// 	arch.getRPG0().internalStore();
	// 	//testing if RPG stores the number 10
	// 	arch.getRPG0().internalRead();
	// 	assertEquals(10, arch.getIntbus1().get());

	// 	//destroying data in internal bus 1
	// 	arch.getIntbus1().put(0);
		
	// 	//pc points to 50 (where we suppose the instruction is
	// 	arch.getExtbus1().put(50);
	// 	arch.getPC().store();

	// 	//now we can perform the inc method. 
	// 	arch.inc();
	// 	arch.getRPG0().internalRead();
	// 	//the externalbus1 must contains the number 11
	// 	assertEquals(11, arch.getIntbus1().get());
		
	// 	//PC must be pointing ONE position after its original value, because this command has no parameters!
	// 	arch.getPC().read();
	// 	assertEquals(51, arch.getExtbus1().get());

	// }
	
	@Test
	public void testIncReg() {
		Architecture arch = new Architecture();
		
		//Stores the number register into the memory in the position 71
	    arch.getMemory().getDataList()[71] = 1;
	    
	    //Set the pc Value to 69
	    arch.getExtbus1().put(70);
	    arch.getPC().store();
	    
	    arch.getExtbus1().put(0);
	    
	    //Stores the value 25 into the Register 1
	    arch.getIntbus1().put(-1);
	    arch.getRPG1().internalStore();

	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Now the addMemReg command can be executed 
	    //In the end of the execution the value in Reg0 must be 8
	    arch.incReg();
	    arch.getRPG1().internalRead();
	    assertEquals(0, arch.getIntbus1().get());
	    
	    //PC must be pointing to 72
	    arch.getPC().read();
	    assertEquals(72, arch.getExtbus1().get());
	    
	    //The value of the memory in position 70 must be 100
	    arch.getExtbus1().put(71);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    
	    //The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(1, arch.getFlags().getBit(0));
	    assertEquals(0, arch.getFlags().getBit(1));
	}
	
	
	
	@Test
	public void testJmp() {
		Architecture arch = new Architecture();
		//storing the number 10 in PC
		arch.getIntbus2().put(10);
		arch.getPC().internalStore();

		//storing the number 25 in the memory, in the position just before that one adressed by PC
		arch.getExtbus1().put(11); //the position is 11, once PC points to 10
		arch.getMemory().store();
		arch.getExtbus1().put(25);
		arch.getMemory().store();
		
		
		//testing if PC stores the number 10
		arch.getPC().read();
		assertEquals(10, arch.getExtbus1().get());
		
		//now we can perform the jmp method. 
		//we will move the the number 25 (stored in the 31th position in the memory) 
		//into the PC
		arch.jmp();
		arch.getPC().internalRead();
		//the internalbus2 must contains the number 25
		assertEquals(25, arch.getIntbus2().get());

	}
	
	@Test
	public void testJn() {
		Architecture arch = new Architecture();
		
		//storing the number 30 in PC
		arch.getIntbus2().put(30);
		arch.getPC().internalStore();
		
		//storing the number 25 in the into the memory, in position 31, the position just after PC
		arch.getExtbus1().put(31);
		arch.getMemory().store();
		arch.getExtbus1().put(25);
		arch.getMemory().store();

		//now we can perform the jn method. 

		//CASE 1.
		//Bit NEGATIVE is equals to 1
		arch.getFlags().setBit(1, 1);
		
		//So, we will move the the number 25 (stored in the 31th position in the memory) 
		//into the PC

		//testing if PC stores the number 30
		arch.getPC().read();
		assertEquals(30, arch.getExtbus1().get());		

		arch.jn();
		
		//PC must be storng the number 25
		arch.getPC().internalRead();
		assertEquals(25, arch.getIntbus2().get());
		
		//CASE 2.
		//Bit NEGATIVE is equals to 0
		arch.getFlags().setBit(1, 0);
		//PC must have the number 30 initially (in this time, by using the external bus)
		arch.getExtbus1().put(30);
		arch.getPC().store();
		//destroying the data in external bus
		arch.getExtbus1().put(0);

		//testing if PC stores the number 30
		arch.getPC().read();
		assertEquals(30, arch.getExtbus1().get());	
		
		//Note that the memory was not changed. So, in position 31 we have the number 25
		
		//Once the ZERO bit is 0, we WILL NOT move the number 25 (stored in the 31th position in the memory)
		//into the PC.
		//The original PC position was 30. The parameter is in position 31. So, now PC must be pointing to 32
		arch.jn();
		//PC contains the number 32
		arch.getPC().internalRead();
		assertEquals(32, arch.getIntbus2().get());
	}

	@Test
	public void testJz() {
	    Architecture arch = new Architecture();
	    
	    // Sets PC's value to the position 30 of the memory
	    arch.getIntbus2().put(30);
	    arch.getPC().internalStore();

	    // Memory position 31 contains the jump address
	    arch.getExtbus1().put(31);
	    arch.getMemory().store();
		arch.getExtbus1().put(50);
		arch.getMemory().store();
	    
	    // Case 1: Bit ZERO is 1 (true condition, should jump)
	    
	    // Updates the values of the flags register and memory
	    arch.getFlags().setBit(0, 1); // Sets bit ZERO to 1 (true condition)
	    
	    // Checks if PC store the number 30
	    arch.getPC().read();
	    assertEquals(30, arch.getExtbus1().get());
	    
	    // Executes the jz() instruction (conditional jump)
	    arch.jz();
	    
	    // Checks if PC was updated to the jump address
	    arch.getPC().internalRead();
	    assertEquals(50, arch.getIntbus2().get());
	    
	    // Case 2: Bit ZERO is 0 (false condition, should not jump)
	    // Reconfigures PC and flags
	    arch.getFlags().setBit(0, 0); // Sets bit ZERO to 0
	    
	    //PC must have the number 30 initially (in this time, by using the external bus)
		arch.getExtbus1().put(30);
		arch.getPC().store();
		
		//Destroying the data in external bus
		arch.getExtbus1().put(0);
	    
	    // Checks if PC store the number 30
	    arch.getPC().read();
	    assertEquals(30, arch.getExtbus1().get());
	    
	    // Executes the jz() instruction (conditional jump)
	    arch.jz();

	    // Testing if PC points to 3 positions after the original
	    // PC was pointing to 30; now it must be pointing to 33
	    arch.getPC().internalRead();
	    assertEquals(32, arch.getIntbus2().get());
	}
	
	@Test
	public void testJeq() {
	    Architecture arch = new Architecture();
	    
	    //Stores the value 35 into the registers 0 and 1
	    arch.getIntbus1().put(35);
	    arch.getRPG0().internalStore();
	    arch.getRPG1().internalStore();
	    
	    //Stores the value 13 into the register 2
	    arch.getIntbus1().put(13);
	    arch.getRPG2().internalStore();
	    //Stores the value 14 into the register 3
	    arch.getIntbus1().put(14);
	    arch.getRPG3().internalStore();
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Sets PC's value to 15
	    arch.getExtbus1().put(15);
	    arch.getPC().store();
	    //Clears external bus
	    arch.getExtbus1().put(0);
	    
	    //Stores the values into the memory
	    arch.getMemory().getDataList()[16] = 0;
	    arch.getMemory().getDataList()[17] = 1;
	    arch.getMemory().getDataList()[18] = 24;
	    arch.getMemory().getDataList()[25] = 2;
	    arch.getMemory().getDataList()[26] = 3;
	    arch.getMemory().getDataList()[27] = 15;
	    
	    //Now the command can be executed for the FIRST TEST
	    //In this case, the result must be true and PC must point in the end to 24
	    arch.jeq();
	    
	    arch.getPC().read();
	    assertEquals(24, arch.getExtbus1().get());
	    
	    //The values of registers must continue the same as in the beginning of the operation
	    arch.getRPG0().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG1().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG2().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    arch.getRPG3().internalRead();
	    assertEquals(14, arch.getIntbus1().get());
	    
	    //Also, the values stored in the memory must have remained the same
	    arch.getExtbus1().put(16);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
	    arch.getExtbus1().put(17);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    arch.getExtbus1().put(18);
	    arch.getMemory().read();
	    assertEquals(24, arch.getExtbus1().get());
	    
	    //The bit zero of flags must be 1
	    assertEquals(1, arch.getFlags().getBit(0));
	    
	    //Now the SECOND TEST can be executed, in this case PC starts in 25
	    arch.jeq();
	    
	    //PC must Store 28
	    arch.getPC().read();
	    assertEquals(28, arch.getExtbus1().get());
	    
	    //The values of registers must continue the same as in the beginning of the operation
	    arch.getRPG0().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG1().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG2().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    arch.getRPG3().internalRead();
	    assertEquals(14, arch.getIntbus1().get());
	    
	    //Also, the values stored in the memory must have remained the same
	    arch.getExtbus1().put(25);
	    arch.getMemory().read();
	    assertEquals(2, arch.getExtbus1().get());
	    arch.getExtbus1().put(26);
	    arch.getMemory().read();
	    assertEquals(3, arch.getExtbus1().get());
	    arch.getExtbus1().put(27);
	    arch.getMemory().read();
	    assertEquals(15, arch.getExtbus1().get());
	    
	    // The flag bit zero must be 0, and bit negative must be 0
	    assertEquals(0, arch.getFlags().getBit(0));
		assertEquals(1, arch.getFlags().getBit(1));
	}
	
	@Test
	public void testJneq() {
	    Architecture arch = new Architecture();
	    
	    //Stores the value 35 into the registers 0 and 1
	    arch.getIntbus1().put(35);
	    arch.getRPG0().internalStore();
	    arch.getIntbus1().put(30);
	    arch.getRPG1().internalStore();
	    
	    //Stores the value 13 into the register 2
	    arch.getIntbus1().put(13);
	    arch.getRPG2().internalStore();
	    //Stores the value 14 into the register 3
	    arch.getIntbus1().put(13);
	    arch.getRPG3().internalStore();
	    //Clears the internal bus 1
	    arch.getIntbus1().put(0);
	    
	    //Sets PC's value to 15
	    arch.getExtbus1().put(15);
	    arch.getPC().store();
	    //Clears external bus
	    arch.getExtbus1().put(0);
	    
	    //Stores the values into the memory
	    arch.getMemory().getDataList()[16] = 0;
	    arch.getMemory().getDataList()[17] = 1;
	    arch.getMemory().getDataList()[18] = 24;
	    arch.getMemory().getDataList()[25] = 2;
	    arch.getMemory().getDataList()[26] = 3;
	    arch.getMemory().getDataList()[27] = 15;
	    
	    //Now the command can be executed for the FIRST TEST
	    //In this case, the result must be true and PC must point in the end to 24
	    arch.jneq();
	    
	    arch.getPC().read();
	    assertEquals(24, arch.getExtbus1().get());
	    
	    //The values of registers must continue the same as in the beginning of the operation
	    arch.getRPG0().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG1().internalRead();
	    assertEquals(30, arch.getIntbus1().get());
	    arch.getRPG2().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    arch.getRPG3().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    
	    //Also, the values stored in the memory must have remained the same
	    arch.getExtbus1().put(16);
	    arch.getMemory().read();
	    assertEquals(0, arch.getExtbus1().get());
	    arch.getExtbus1().put(17);
	    arch.getMemory().read();
	    assertEquals(1, arch.getExtbus1().get());
	    arch.getExtbus1().put(18);
	    arch.getMemory().read();
	    assertEquals(24, arch.getExtbus1().get());
	    
	    //The bit zero of flags must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    
	    //Now the SECOND TEST can be executed, in this case PC starts in 25
	    arch.jneq();
	    
	    //PC must Store 28
	    arch.getPC().read();
	    assertEquals(28, arch.getExtbus1().get());
	    
	    //The values of registers must continue the same as in the beginning of the operation
	    arch.getRPG0().internalRead();
	    assertEquals(35, arch.getIntbus1().get());
	    arch.getRPG1().internalRead();
	    assertEquals(30, arch.getIntbus1().get());
	    arch.getRPG2().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    arch.getRPG3().internalRead();
	    assertEquals(13, arch.getIntbus1().get());
	    
	    //Also, the values stored in the memory must have remained the same
	    arch.getExtbus1().put(25);
	    arch.getMemory().read();
	    assertEquals(2, arch.getExtbus1().get());
	    arch.getExtbus1().put(26);
	    arch.getMemory().read();
	    assertEquals(3, arch.getExtbus1().get());
	    arch.getExtbus1().put(27);
	    arch.getMemory().read();
	    assertEquals(15, arch.getExtbus1().get());
	    
	    //The bit zero of flags must be 0
	    assertEquals(1, arch.getFlags().getBit(0));
	}
	
	@Test
	public void testJgt(){
		Architecture arch = new Architecture();

		//storing the number 1 in the memory, in position 31
		arch.getMemory().getDataList()[46]=1;
		//storing the number 0 in the memory, in position 32
		arch.getMemory().getDataList()[47]=0;
		//making PC points to position 30
		arch.getMemory().getDataList()[48]=30;
		arch.getExtbus1().put(45);
		arch.getPC().store();
		
		//now setting the registers values
		arch.getIntbus1().put(45);
		arch.getRegistersList().get(0).internalStore(); //RPG0 has 45
		arch.getIntbus1().put(45);
		arch.getRegistersList().get(1).internalStore(); //RPG1 has 99

		arch.jgt();
		
		arch.getRegistersList().get(0).internalRead();
		assertEquals(45, arch.getIntbus1().get());
		arch.getRegistersList().get(1).internalRead();
		assertEquals(45, arch.getIntbus1().get());

		arch.getPC().read();
		assertEquals(30, arch.getExtbus1().get());
	}
	
	@Test
	public void testJlw() {
		
		Architecture arch = new Architecture();
		
		// storing the number 0 in the memory, in position 31
		arch.getMemory().getDataList()[31]=0;
		// storing the number 1 in the memory, in position 32
		arch.getMemory().getDataList()[32]=1;
		// Stores the number 50 in the position 33 of the memory
	    arch.getMemory().getDataList()[33] = 50;
		
	    // Making PC points to position 30
  		arch.getExtbus1().put(30);
  		arch.getPC().store();
	    
	    // Now setting the registers values
	    arch.getIntbus1().put(5);
	    arch.getRPG0().internalStore(); // RPG0 has 5
	    arch.getIntbus1().put(7);
	    arch.getRPG1().internalStore(); // RPG1 has 7
		
	    // Executing the command jlw
	    arch.jlw();
	    
	    // The value stored into the Register 0 must be 5
	    arch.getRPG0().internalRead();
		assertEquals(5, arch.getIntbus1().get());
		// The value stored into the Register 1 must be 7
		arch.getRPG1().internalRead();
		assertEquals(7, arch.getIntbus1().get());

		arch.getPC().read();
		assertEquals(50, arch.getExtbus1().get());

		// The flag bit zero must be 0, and bit negative must be 1
	    assertEquals(0, arch.getFlags().getBit(0));
	    assertEquals(1, arch.getFlags().getBit(1));	
	}
	
	@Test
	public void testRead() {
		Architecture arch = new Architecture();
		//storing the number 10 in RPG
		arch.getIntbus1().put(10);
		arch.getRPG0().internalStore();
		//testing if RPG stores the number 10
		arch.getRPG0().internalRead();
		assertEquals(10, arch.getIntbus1().get());
		
		//storing the number 25 in the memory, in position 31
		arch.getExtbus1().put(31);
		arch.getMemory().store();
		arch.getExtbus1().put(25);
		arch.getMemory().store();
		
		//storing the number -100 in the memory, in position 25
		arch.getExtbus1().put(25);
		arch.getMemory().store();
		arch.getExtbus1().put(-100);
		arch.getMemory().store();
		
		//PC must be pointing to the address just before the parameter (where is the instruction)
		arch.getExtbus1().put(30);
		arch.getPC().store();

		//now we can perform the read method. 
		//scenery PC=30, mem[31]=25, mem[25]=-100 RPG=10
		//we will move the the number 25 (stored in the 31th position in the memory) 
		//into the RPG by using the move command (move 31)

		arch.read();
		arch.getRPG0().internalRead();
		//the internalbus1 must contain the number -100 (that is stored in position 25)
		assertEquals(-100, arch.getRPG0().getData());
		
		//PC must be pointing two positions after its original value
		arch.getPC().read();
		assertEquals(32, arch.getExtbus1().get());

	}
	
	@Test
	public void testStore() {
		Architecture arch = new Architecture();

		//storing the number 25 in the memory, in position 31
		arch.getMemory().getDataList()[31]=25;
		

		//now we can perform the store method. 
		//store X stores, in the position X, the data that is currently in RPG
		
		//let's put PC pointing to the position 11
		arch.getExtbus1().put(11);
		arch.getPC().store();
		//now lets put the parameter (the position where the data will be stored) into the position next to PC
		arch.getMemory().getDataList()[12]=31;
		
		//storing the number 155 in RPG
		arch.getIntbus1().put(155);
		arch.getRPG0().internalStore();
		
		//testing if memory contains the number 25 in the 31th position
		arch.getExtbus1().put(31);
		arch.getMemory().read();
		assertEquals(25, arch.getExtbus1().get());
		
		//So, PC is pointing to memory[11], memory[12] has 31, memory [31] has 25 and RPG has 155
		
		//destroying data in externalbus 1
		arch.getExtbus1().put(0);

		arch.store();

		//now, memory[31] must be updated from 25 to 155
		assertEquals(155, arch.getMemory().getDataList()[31]);
		
		//PC must be pointing two positions after its original value
		arch.getPC().read();
		assertEquals(13, arch.getExtbus1().get());
	}
	
	@Test
	public void testLdi() {
		Architecture arch = new Architecture();
		//storing the number 10 in RPG
		arch.getIntbus1().put(10);
		arch.getRPG0().internalStore();
		
		//the scenery: PC points to 50, mem[51] (parameter) is -40
		
		arch.getExtbus1().put(51);
		arch.getMemory().store();
		arch.getExtbus1().put(-40);
		arch.getMemory().store();
		
		arch.getExtbus1().put(50);
		arch.getPC().store();
		
		//destroying data in internalbus 1
		arch.getIntbus1().put(0);

		//now we can perform the ldi method. 
		//we will move the the number -40 (immediate value) 
		//into the rpg
		arch.ldi();
		
		arch.getRPG0().internalRead();
		//the externalbus1 must contains the number 44
		assertEquals(-40, arch.getIntbus1().get());
		
		//PC must be pointing two positions after its original value
		arch.getPC().read();
		assertEquals(52, arch.getExtbus1().get());

	}
		
	@Test
	public void testFillCommandsList() {
		
		//all the instructions must be in Commands List
		/*
		 *
				add addr (rpg <- rpg + addr)
				sub addr (rpg <- rpg - addr)
				jmp addr (pc <- addr)
				jz addr  (se bitZero pc <- addr)
				jn addr  (se bitneg pc <- addr)
				read addr (rpg <- addr)
				store addr  (addr <- rpg)
				ldi x    (rpg <- x. x must be an integer)
				inc    (rpg++)
				move %reg0 %reg1 (reg1 <- Reg0)
				add mem %regA (regA <- memory[mem] + RegA)
				sub %regA mem (memory[mem] <- regA - memory[mem])
				move imm %regA (regA <- immediate)
				jeq %regA %regB mem (if regA == regB, PC <- mem)
		 */
		
		Architecture arch = new Architecture();
		ArrayList<String> commands = arch.getCommandsList();
		//assertTrue("add".equals(commands.get(0)));
		assertTrue("addRegReg".equals(commands.get(0)));
		assertTrue("addMemReg".equals(commands.get(1)));
		assertTrue("addRegMem".equals(commands.get(2)));
		assertTrue("addImmReg".equals(commands.get(3)));
		//assertTrue("sub".equals(commands.get(5)));
		assertTrue("subRegReg".equals(commands.get(4)));
		assertTrue("subMemReg".equals(commands.get(5)));
		assertTrue("subRegMem".equals(commands.get(6)));
		assertTrue("subImmReg".equals(commands.get(7)));
		assertTrue("imulMemReg".equals(commands.get(8)));
		assertTrue("imulRegMem".equals(commands.get(9)));
		assertTrue("imulRegReg".equals(commands.get(10)));
		assertTrue("moveMemReg".equals(commands.get(11)));
		assertTrue("moveRegMem".equals(commands.get(12)));
		assertTrue("moveRegReg".equals(commands.get(13)));
		assertTrue("moveImmReg".equals(commands.get(14)));
		//assertTrue("inc".equals(commands.get(17)));
		assertTrue("incReg".equals(commands.get(15)));
		assertTrue("jmp".equals(commands.get(16)));
		assertTrue("jn".equals(commands.get(17)));
		assertTrue("jz".equals(commands.get(18)));
		assertTrue("jeq".equals(commands.get(19)));
		assertTrue("jneq".equals(commands.get(20)));
		assertTrue("jgt".equals(commands.get(21)));
		assertTrue("jlw".equals(commands.get(22)));
		assertTrue("read".equals(commands.get(23)));
		assertTrue("store".equals(commands.get(24)));
		assertTrue("ldi".equals(commands.get(25)));
	}
	
	@Test
	public void testReadExec() throws IOException {
		Architecture arch = new Architecture();
		arch.readExec("testFile");
		assertEquals(5, arch.getMemory().getDataList()[0]);
		assertEquals(4, arch.getMemory().getDataList()[1]);
		assertEquals(3, arch.getMemory().getDataList()[2]);
		assertEquals(2, arch.getMemory().getDataList()[3]);
		assertEquals(1, arch.getMemory().getDataList()[4]);
		assertEquals(0, arch.getMemory().getDataList()[5]);
	}
}