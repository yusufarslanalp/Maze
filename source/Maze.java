package maze;

import java.util.ArrayList;
import javax.swing.JOptionPane;


//Bu sınıfın işlevi:
//Cordinate tipinde bir current noktası alır.
//Aldığı current noktasından başlayarak bütün labirenti gezer ve
//en kısa çıkış patikasını belirler.
public class Maze
{
    Map tiles;
    Cordinate current;

    //Mevcut patika.
    ArrayList<Cordinate> path;
    ArrayList< ArrayList<Cordinate> > exit_paths;
    ArrayList< ArrayList<Cordinate> > registered_points;
    

    //constructor
    Maze( Map get_tiles, Cordinate start )
    {
        current = start;
        tiles = get_tiles;
        path = new ArrayList<Cordinate>();
        exit_paths = new ArrayList< ArrayList<Cordinate> >();
        registered_points = new ArrayList< ArrayList<Cordinate> >();
    }

    ArrayList<Cordinate>
    return_sorthest_exit()
    {
        int min_index = 0;
        int min_size = exit_paths.get(0).size();

        for( int i = 1; i < exit_paths.size(); i++ )
        {
            if( exit_paths.get(i).size() < min_size )
            {
                min_index = i;
                min_size = exit_paths.get(i).size();
            }
        }

        ArrayList<Cordinate> res = new ArrayList<Cordinate>();
        for( int i = 0; i < exit_paths.get( min_index ).size(); i++ )
        {
            res.add( new Cordinate( exit_paths.get( min_index ).get(i) ) );
        }

        return res;
    }

    
    //exit patikalarının kordinatlarını print eder.
    void print_list(  )
    {
        for( int i = 0; i < exit_paths.size(); i++ )
        {
            for( int j = 0; j < exit_paths.get(i).size(); j++ )
            {
                exit_paths.get(i).get(j).print();
            }
            //System.out.println();
        }
    }

    
    //map class'ının print fonksiyonunu çağırır. 
    void print()
    {
        tiles.print();
    }

    //*** Bu programın ve bu sınıfın temel fonksiyonu budur. ***//
    //exit patikalarının hepsiin recursive olarak bulur ve kaydeder.
    void walk(  )
    {
        Cordinate my_current = new Cordinate( current );
        path.add( current );
        
        if( is_exit( my_current ) == true )
        {
            tiles.set_tile( current, "used");
            exit_paths.add( new ArrayList() );
            for( Cordinate iter : path )
            {
                exit_paths.get( exit_paths.size()-1 ).add( new Cordinate( iter ) );
            }
        }

        else
        {
            if( tiles.get_tile( my_current.up() ).equals("empth") )
            {
                //add_registered( my_current );/////////////////////////
                tiles.set_tile( current, "used");
                current = current.up();
                walk( );
            }

            if( tiles.get_tile( my_current.left() ).equals("empth") )
            {
                add_registered( my_current ); //eğer oyuncu dönüş yolumdaysa bu fonksiyona girer. current düzeltildi.
                tiles.set_tile( current, "used");
                current = current.left();
                walk( );    
            }

            if( tiles.get_tile( my_current.right() ).equals("empth") )
            {
                add_registered( my_current );
                tiles.set_tile( current, "used");
                current = current.right();
                walk( );        
            }

            if( tiles.get_tile( my_current.down() ).equals("empth") )
            {
                add_registered( my_current );
                tiles.set_tile( current, "used");
                current = current.down();
                walk( );        
            }
        }
    }
    
    
    //verilen kordinatın komşularından birinin exit olup olmadığını kontrol eder.
    boolean is_exit( Cordinate crd )
    {
        if( tiles.get_tile(crd.up()).equals("exit") ) return true;
        if( tiles.get_tile(crd.left()).equals("exit") ) return true;
        if( tiles.get_tile(crd.right()).equals("exit") ) return true;
        if( tiles.get_tile(crd.down()).equals("exit") ) return true;
        
        return false;
    }
    
    int is_registered( Cordinate crd ) //bu fonksiyon verilen kordinat, registered_points in
    {                                       //listelerinin sıfırıncı elemanlarından birinde varsa 
        for( int i = 0; i < registered_points.size(); i++ )     //listenin indeksini return eder..
        {
            if(registered_points.get(i).get(0).equal(crd) == true ) return i; 
        }
        return -1;
    }
    
    void regulate_path( Cordinate my_current ) //kayıt noktasından sonrasında 
    {
        int make_empth = find_index( path, my_current ) +2;
        int remove = find_index( path, my_current ) +1; //path[remove] indexi ve devamı path listesinden silinecektir.

        for( int i = path.size() -1; i >= make_empth; i-- )
        {
            tiles.set_tile( path.get(i)  , "empth" );
        }
        

        for( int i = path.size() -1; i >= remove; i--  )
        {
            int place = is_registered( path.get(i) );
            if( place >= 0 )
            {
                for( int k = 1; k < registered_points.get(place).size(); k++  )
                {
                    tiles.set_tile( registered_points.get(place).get(k), "empth" );
                }
            
            }
        }
        //print();////////
        for( int i = path.size() -1; i >= remove; i-- )
        {
            path.remove( i );
        }

        current = new Cordinate( my_current );
    }
    
   
    


    void add_registered( Cordinate my_current ) 
    {
        if( current.equal(my_current) == false )//bu koşul önceki iflerden herhangi birinne girilip girilmrdiğini belirler.
        {
            if( is_registered( my_current ) >= 0 ) //daha önceden my_current noktası için kayıt oluşturuldu.
            {
                registered_points.get( is_registered( my_current ) ).add( path.get(find_index( path, my_current ) +1) );
            }
            else    //daha öncaden my_current noktası için kayıt oluşturulmadı
            {
                registered_points.add( new ArrayList() );
                registered_points.get( registered_points.size() -1 ).add(my_current);
                registered_points.get( registered_points.size() -1 ).add( path.get(find_index( path, my_current ) +1) );
            }
        }

        regulate_path( my_current );
    }
 
    
    int find_index( ArrayList<Cordinate> arr, Cordinate crd ) //verilen bir kordinatın listenin kaçıncı index!inde olduğunu return eder.
    {
        for( int i = 0; i < arr.size(); i++ )
            if( arr.get(i).equal( crd ) == true ) return i;
        return -1;
    }
    

    
    public static void main( String args[] )
    {

        
         
         
         Create_map cr_map;
         cr_map = new Create_map( 15, 15);
         cr_map.setVisible(true);
         
         

         //GUI üzerinde solve butonuna basıldıktan sonra çözüm GUI üzerinde gösterilir.
         //daha sonra GUI üzerinde reset tuşuna basarak GUI yi sıfırlayabiliriz.
         //böylece ayn GUI üzerinde tekrar maze oluşturabilir ve çözebiliriz.
         while(true)
         {
             //bu whilw döngüsü programın burada durmasını sağlamıştı.
             //programın devam edebilmesi için "cr_map.is_stop" değerinin false
             //olması gerekmektedir.
             //cr_map'a ait solve butonuna basıldığında "cr_map.is_stop" değeri
             //false olmakta ve program devam etmektedir.             
             while(cr_map.is_stop == true){
                try {
                   Thread.sleep(200);
                } catch(InterruptedException e) {
                }
            }

            if( cr_map.is_avalid() == true )
            {
                //cr_map.map üzerindeki başangıç noktası bulundu
                cr_map.map.start = cr_map.find_start();

                 Maze mz = new Maze( cr_map.get_map() , cr_map.map.start );
                 mz.walk();

                //en kısa çıkış patikası GUI üzerinde gösterildi.
                cr_map.print_shortest_path( mz.return_sorthest_exit() );

                //bu satır sayesinde döngünün başına dönüldüğünde iç kısımdaki
                //while döngüsünde programın durdurulması sağlanır.
                cr_map.is_stop = true;
            }
            
            else
            {
                //prits message box
                System.out.println("invalid map");
                JOptionPane.showMessageDialog(null,
                    "invalid map\nthere should not be 2x2 empty tile in maze");
                cr_map.is_stop = true;
            }
             


         }
    }    
}


/*

*/


