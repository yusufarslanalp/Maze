package maze;


public class Map
{
    String[][] map;
    Cordinate start;

    //constructor
    Map( int x, int y , String get_map[][] )
    {
            map = new String[x][y];
            map = get_map.clone();
    }
    
    //constructor
    Map( int x, int y )
    {
            map = new String[x][y];
    }

    Map( Map right )
    {
        map = new String[right.num_of_line()][right.len_of_line()];
        for( int i = 0; i < right.map.length; i++ )
            for( int j = 0; j < right.map.length; j++  )
            {
                map[i][j] = new String( right.map[i][j] );
            }
    }
    
    boolean
    is_map_valid()
    {
        int check_i = map.length - 1;
        int check_j = map[check_i].length -1;
        
        for( int i = 0; i < check_i; i++ )
        {
            for(int j = 0; j < check_j; j++)
            {
                if( invalid_map_piece(i,j) == true ) return false;
            
            }
        
        }
        return true;
    }
    

    /*
    eğer map arrayinin verilen noktası için
    E  E
    E  E
    şeklinde 2x2 lik bir empty bölge varsa true return eder.
    Çünkü o noktada invalid bir bölge vardır.
    */
    boolean
    invalid_map_piece(int i, int j)
    {
        if( map[i][j].equals("empth") &&
            map[i][j+1].equals("empth") &&
            map[i+1][j].equals("empth") &&
            map[i+1][j+1].equals("empth")   
          ) return true;
        
        else return false;
    }
	
    int num_of_line()
    {
        return map.length;
    }
    
    int len_of_line()
    {
        return map[0].length;
    }

    //returns tile type
    String get_tile( Cordinate crd )
    {
        return map[crd.x][crd.y];
    }

    String get_tile( int x, int y )
    {
        return map[x][y];
    }
    
    //sets tile type
    void set_tile( Cordinate crd, String str )
    {
        map[crd.x][crd.y] = str;
    }

    //sets tile type
    void set_tile( int x, int y, String str )
    {
        map[x][y] = str;
    }

    
    void print()
    {
        int i,j;
        
        for( i = 0; i < map.length; i++ )
        {
            for( j = 0; j < map[i].length; j++ )
            {
                if( map[i][j].equals( "wall" ) ) System.out.print( "W " );
                else if( map[i][j].equals( "empth" ) ) System.out.print( "E " );
                else if( map[i][j].equals( "used" ) ) System.out.print( "U " );
                else if( map[i][j].equals( "exit" ) ) System.out.print( "X " );
                else System.out.print( "P " );
                //System.out.print( map[i][j] + " " );
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
