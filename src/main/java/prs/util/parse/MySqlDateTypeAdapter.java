package prs.util.parse;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySqlDateTypeAdapter extends TypeAdapter<java.sql.Date>{
	    @Override
	    public void write(JsonWriter out, java.sql.Date value) throws IOException {
	        if (value == null)
	            out.nullValue();
	        else

	            out.value(value.getTime());
	    }

	    @Override
	    public java.sql.Date read(JsonReader in) throws IOException {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			Date parsed = null;
			String inputDate = in.nextString();
	    	if (in != null) {
				try {
					parsed = (sdf.parse(inputDate));
				} catch (ParseException e) {
					System.out.println("Unparseable date: \"" + inputDate + "\". Supported formats: yyyy-MM-dd");
				}
				return new java.sql.Date(parsed.getTime());
			}

	        else
	            return null;
	    }

}