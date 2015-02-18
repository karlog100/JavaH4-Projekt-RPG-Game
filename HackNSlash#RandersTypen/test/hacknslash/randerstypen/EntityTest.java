/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hacknslash.randerstypen;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author computer
 */
public class EntityTest {
    
    public EntityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of MaxHealth method, of class Entity.
     */
    @Test
    public void testMaxHealth() {
        System.out.println("MaxHealth");
        Entity instance = new Entity();
        int expResult = 20;
        instance.Health = 0;
        instance.MaxHealth = 20;
        instance.MaxHealth();
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(expResult, instance.Health);
    }

    /**
     * Test of Attack method, of class Entity.
     */
    @Test
    public void testAttack() {
        System.out.println("Attack");
        Entity instance = new Entity();
        Object result = instance.Attack();
        assertTrue(result instanceof Integer);
    }

    /**
     * Test of Health method, of class Entity.
     */
    @Test
    public void testHealth() {
        System.out.println("Health");
        Entity instance = new Entity();
        int expResult = 0;
        instance.Health = 0;
        int result = instance.Health();
        assertEquals(expResult, result);
        expResult = 20;
        instance.MaxHealth = 20;
        instance.MaxHealth();
        result = instance.Health();
        assertEquals(expResult, result);
    }

    /**
     * Test of Level method, of class Entity.
     */
    @Test
    public void testLevel() {
        System.out.println("Level");
        Entity instance = new Entity();
        instance.Level = 20;
        int expResult = 20;
        int result = instance.Level();
        assertEquals(expResult, result);
    }

    /**
     * Test of DamageCalculator method, of class Entity.
     */
    @Test
    public void testDamageCalculator() {
        System.out.println("DamageCalculator");
        Entity instance = new Entity();
        instance.Level = 5;
        Object result = instance.DamageCalculator();
        assertTrue(result instanceof Integer);
    }

    /**
     * Test of DamageTaken method, of class Entity.
     */
    @Test
    public void testDamageTaken() {
        System.out.println("DamageTaken");
        int Damage = 10;
        Entity instance = new Entity();
        instance.Health = 11;
        instance.DamageTaken(Damage);
        assertEquals(instance.Health, 1);
    }
    
}
