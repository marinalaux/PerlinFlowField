/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perlinflowfield;

/**
 *
 * @author Marina
 */
public class Ball {
    
    public Vector pos;
    public Vector vel;
    public Vector acc;
    public static final double limite = 0.3;

    public Ball(Vector pos) {
        this.pos = pos;
        vel = new Vector();
        acc = new Vector();
    }
    
    public void update() {
        vel.add(acc);
        pos.add(vel);
        
        if(vel.x > limite) {
            vel.x = limite;
        }
        if(vel.x < -limite) {
            vel.x = -limite;
        }
        if(vel.y > limite) {
            vel.y = limite;
        }
        if(vel.y < -limite) {
            vel.y = -limite;
        }
        
        acc = new Vector();
    }
    
}
