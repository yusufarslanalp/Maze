package maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;


//Maze haritasını oluşturmak için gerekli GUI'yi sağlayan class'tır.
public class Create_map extends javax.swing.JFrame {

    Map map;
    JButton[][] button_arr;
    JButton stop_button; //bu butona basıldığında GUI ile oluşturulan map !!aktarılır.
    JButton reset_button;
    public static volatile boolean is_stop = true;

    //constructor
    public Create_map( int num_of_rows, int num_of_cols ) {
        map = new Map( num_of_rows, num_of_cols );
        button_arr = new JButton[ num_of_rows ][num_of_cols];
        stop_button = new JButton( "SOLVE" ); ////////
        reset_button = new JButton( "RESET" );
        stop_button.setBounds(700, 50, 80, 30);
        reset_button.setBounds(700, 100, 80, 30);
        this.add( stop_button );
        this.add( reset_button );
        
        //Bu butona basılmadan arayüzde oluşturulan harita //aktarılmaz
        //ve bu butona basılmadan program devam etmez
        stop_button.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e )
            {
                is_stop = false;
            }
        } );
        
        reset_button.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e )
            {
                reset();
            }
        } );        
        
        initComponents();
        init_map();
        
        this.setTitle("Create Map");
        call_create_buttons( );
        init_buttons();
        this.setSize(800, 800);
        
        
    }

    void reset()
    {
        init_buttons();
        init_map();

        for( int i = 0; i < button_arr.length; i++ )
            for( int j = 0 ; j < button_arr[i].length; j++ )
                button_arr[i][j].setText("");
    
    }
         

    
    Map
    get_map()
    {
        return new Map(map);
    }
    
    void set_map( Map mp )
    {
        map = mp;
    }


    void
    print_shortest_path( ArrayList< Cordinate > shortest_exit )
    {
        int x;
        int y;
        
        for( int i = 0; i < shortest_exit.size(); i++ )
        {
            x = shortest_exit.get(i).x;
            y = shortest_exit.get(i).y;
            
            button_arr[x][y].setFont(new Font("", Font.BOLD, 15));
            button_arr[x][y].setText("O");
        
        }
    }


    private void
    call_create_buttons( )
    {
       int button_width;
       int button_height;
       int num_of_row = button_arr.length;
       int num_of_col = button_arr[0].length;

        if( num_of_row > 12 || num_of_col > 12 )
        {
            int max = num_of_row;
            if ( num_of_col > num_of_row ) max = num_of_col;
            
            button_width = 700 / max;
            button_height = 700 / max;
            create_buttons( button_width, button_height );

        }
        
        else create_buttons( 50, 50 );
    }


    //button_arr arrayindeki butonların initialize edilmesi işlemi bu fonksiyonde yapılır.
    //butonlara fonksiyonlar burada eklenir.
    private void
    create_buttons( int button_width, int button_height )
    {
        int center_x = 0;
        int center_y = 0;

        
        for( int i = 0; i < button_arr.length; i++ )
        {
            for( int j = 0; j < button_arr[i].length; j++ )
            {
                button_arr[i][j] = new JButton("");
                button_arr[i][j].setBounds(center_x, center_y, button_width, button_height);
                center_x += button_width;
                
                this.add( button_arr[i][j] );
                button_arr[i][j].addActionListener( new My_button( i,j ){

                    public void actionPerformed( ActionEvent e )
                    {
                        if( map.get_tile( row, col ).equals("empth") )
                        {
                            map.set_tile(row, col, "exit");
                            button_arr[row][col].setBackground(Color.red);
                        }
                        
                        else if(  map.get_tile( row, col ).equals("wall") )
                        {
                            map.set_tile(row, col, "empth");
                            button_arr[row][col].setBackground(Color.white);                       
                        }
                        
                        else if(  map.get_tile( row, col ).equals("exit") )
                        {
                            map.set_tile(row, col, "start");
                            button_arr[row][col].setBackground(Color.blue);                        
                        }

                        else if(  map.get_tile( row, col ).equals("start") )
                        {
                            map.set_tile(row, col, "wall");
                            button_arr[row][col].setBackground(Color.black);                        
                        }

                    }

                } );
            }
            center_x = 0;
            center_y += button_height;
        }
    }

    Cordinate
    find_start()
    {
        for( int i = 0; i < button_arr.length; i++ )
        {
            for( int j = 0; j < button_arr[i].length; j++ )
            {
                if( map.get_tile(i, j) .equals("start") )
                {
                    return new Cordinate( i,j );
                }
            }        
        }
        
        return new Cordinate( -1, -1 );
    }

    //tüm butonları başlangıçta beyaz yapar.
    private void
    init_buttons()
    {
        for( int i = 0; i < button_arr.length; i++ )
            for( int j = 0; j < button_arr[i].length; j++ )
                button_arr[i][j].setBackground(Color.black);
    }
    
    //tüm butonları başlangıçta boş yapar.
    private void
    init_map()
    {
        for( int i = 0; i < map.num_of_line(); i++ )
            for( int j = 0; j < map.len_of_line(); j++  )
                map.set_tile(i, j, "wall");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        
    // Variables declaration - do not modify                     
    // End of variables declaration                   
}

/*


*/

