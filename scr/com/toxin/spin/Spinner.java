package com.toxin.spin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

class Spinner extends Canvas implements Runnable
{
    private JFrame frame;
    private Thread th;
    private double speed = 0;
    private double dspeed = 0;
    private Image img;
    private int index;
    BufferStrategy bs;

    public Spinner()
    {
        super();

        index = (int)(Math.random() * 6 + 1);
        System.out.println(index);
        String fileRes = "/res/spinner_img_" + index + ".png";
        img = new ImageIcon(getClass().getResource(fileRes)).getImage();

        addKeyListener(new ThisKeyListener());
        setFocusable(true);

        frame = new JFrame("Super Puper Mega WAW Classs Java Spinner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(img.getWidth(null), img.getHeight(null));
        frame.setVisible(true);

        createBufferStrategy(3);
        bs = getBufferStrategy();

        paintSpinner();

        th = new Thread(this);
        th.start();
    }

    private void update()
    {
        speed += dspeed;
        if(dspeed > Math.PI / 1000)
            dspeed -= Math.PI / 1000;
        else
            dspeed = 0;
    }

    private void paintSpinner()
    {
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.translate(getWidth() / 2, getHeight() / 2);
        g.rotate(speed);

        g.drawImage(img, -getWidth() / 2, -getHeight() / 2, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args)
    {
        Spinner sp = new Spinner();
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double partTime = 1_000_000_000.0 / 60.0;
        double delta = 0;
        while(true)
        {
            long nowTime = System.nanoTime();
            delta += nowTime - lastTime;
            lastTime = nowTime;
            if(delta >= partTime)
            {
                update();
                paintSpinner();
                delta = 0;
            }
        }
    }

    private class ThisKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                dspeed += Math.PI / 10;
            }
        }
    }
}
