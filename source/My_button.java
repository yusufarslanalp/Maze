package maze;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//my button class'ı action performed fonksiyonuna row ve col değerlerini
//parametre olarak gönderebilmek için oluşturulmuştur.
public abstract class My_button implements ActionListener
{
    int row;
    int col;
    
    My_button( int r, int c )
    {
        row = r;
        col = c;
    }
}

