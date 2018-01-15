package servlet;
import servlet.Film;
import java.util.Date;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name="Film List",urlPatterns={"/"} )
public class FilmList extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Film> films;

    public FilmList()
    {
    	films = new ArrayList<>();
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        out.print( getIndexHtml() );
        out.flush();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String dateStr = request.getParameter( "date" );
        DateFormat format = new SimpleDateFormat( "dd-MM-yyyy" );

        Film film= new Film();
        film.Film = request.getParameter( "film" );
        film.Producer = request.getParameter( "producer" );
        film.Rating = Float.parseFloat( request.getParameter( "rating" ) );
        try {
        	film.Year = format.parse( dateStr );
        } catch ( ParseException ignored ) {
        	film.Year = new Date( 15, 01, 2018);
        }

        films.add(film);

        PrintWriter out = response.getWriter();
        out.print( getIndexHtml() );
        out.flush();
    }

    private String getIndexHtml() {
        String addFilmHtml = "<html>" +
                "<head>" +
                "<title>" +
                "Films" +
                "</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"CarController\" method=\"POST\">" +
                "Film: <input type=\"text\" name=\"film\">" +
                "<br />" +
                "Producer: <input type=\"text\" name=\"producer\" />" +
                "<br />" +
                "Rating: <input type=\"number\" name = \"rating\" />" +
                "<br/>" +
                "Date(dd-MM-yyyy): <input type=\"date\" name=\"date\" />" +
                "<br />" +
                "<input type=\"submit\" value=\"Add film\" />" +
                "</form>";

        String filmsListHtml =
                "<details>" +
                "<summary>Added films</summary>" +
                "<table>" +
                "<tr>" +
                "<th>Film</th>" +
                "<th>Producer</th>" +
                "<th>Rating</th>" +
                "<th>Release Date</th>" +
                "</tr>";

        for ( Film film: films ) {
        	filmsListHtml +=
                    "<tr>" +
                    "<td>" + film.Film + "</td>" +
                    "<td>" + film.Producer + "</td>" +
                    "<td>" + film.Rating + "</td>" +
                    "<td>" + new SimpleDateFormat("dd-MM-yyyy").format(film.Year ) + "</td>" +
                    "</tr>";
        }

        filmsListHtml +=
                "</table>" +
                "</details>" +
                "</body>" +
                "</html>";

        return addFilmHtml + filmsListHtml;
    }
}