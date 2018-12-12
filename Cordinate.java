package maze;

public class Cordinate
{
    int x;
    int y;

    //constructor
    Cordinate( int new_x, int new_y )
    {
        x = new_x;
        y = new_y;
    }

    //constructor
    Cordinate()
    {
	x = 0;
        y = 0;
    }
    
    //copy constructor
    Cordinate( Cordinate right )
    {
	x = right.x;
        y = right.y;
    }
    
    boolean equal( Cordinate crd )
    {
        if( x == crd.x && y == crd.y ) return true;
        else return false;
    }

    Cordinate up( )
    {
        Cordinate new_crd = new Cordinate( x -1 , y );
        return new_crd;
    }
    
    Cordinate left( )
    {
        Cordinate new_crd = new Cordinate( x , y -1 );
        return new_crd;
    }    
    
    Cordinate right( )
    {
        Cordinate new_crd = new Cordinate( x , y +1 );
        return new_crd;
    }
    
    Cordinate down( )
    {
        Cordinate new_crd = new Cordinate( x +1 , y );
        return new_crd;
    }
    
    
    void print()
    {
        System.out.print( "(" + x + "," + y + ")" );
    }


}

