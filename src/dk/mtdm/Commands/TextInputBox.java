package dk.mtdm.Commands;

import dk.mtdm.location.LDVector;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.HashMap;
import java.util.Map;

public class TextInputBox {
    private final int width;
    private final int height;
    private String text = "";
    Map<Integer,Character> charLookUp = new HashMap<>();

    public TextInputBox(PApplet p) {
        width = p.width;
        height = 50;
        loadHashmap();
    }

    public void show(PGraphics g) {
        LDVector pos = new LDVector(20,g.height - height); // Canvas pos
        g.rect(pos.getX(), pos.getY(), g.width, height);
        int margin = 5;
        g.push();
        g.fill(0);
        g.text(text,pos.getX() + margin, pos.getY() + margin, g.width - margin * 2, height - margin * 2);
        g.pop();
    }

    public void keyPressed(int c) {
        if(charLookUp.get(c) != null) {
            text += charLookUp.get(c);
        }
    }

    public String getText() {
        return text;
    }

    private void loadHashmap() {
        charLookUp.put(81,'q');
        charLookUp.put(87,'w');
        charLookUp.put(69,'e');
        charLookUp.put(82,'r');
        charLookUp.put(84,'t');
        charLookUp.put(89,'y');
        charLookUp.put(85,'u');
        charLookUp.put(73,'i');
        charLookUp.put(79,'o');
        charLookUp.put(80,'p');
        charLookUp.put(221,'å');

        charLookUp.put(65,'a');
        charLookUp.put(83,'s');
        charLookUp.put(68,'d');
        charLookUp.put(70,'f');
        charLookUp.put(71,'g');
        charLookUp.put(72,'h');
        charLookUp.put(74,'j');
        charLookUp.put(75,'k');
        charLookUp.put(76,'l');
        charLookUp.put(192,'æ');
        charLookUp.put(222,'ø');

        charLookUp.put(90,'z');
        charLookUp.put(88,'x');
        charLookUp.put(67,'c');
        charLookUp.put(86,'v');
        charLookUp.put(66,'b');
        charLookUp.put(78,'n');
        charLookUp.put(77,'m');
        charLookUp.put(188,',');
        charLookUp.put(190,'.');

        charLookUp.put(49,'1');
        charLookUp.put(50,'2');
        charLookUp.put(51,'3');
        charLookUp.put(52,'4');
        charLookUp.put(53,'5');
        charLookUp.put(54,'6');
        charLookUp.put(55,'7');
        charLookUp.put(56,'8');
        charLookUp.put(57,'9');
        charLookUp.put(48,'0');
    }
}
