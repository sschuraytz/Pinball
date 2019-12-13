package pinball;

import com.google.gson.Gson;
import pinball.DTO.BodiesDTO;
import pinball.DTO.BodyDTO;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONBodiesParser
{
    private BodiesDTO bodiesDTO;
    public JSONBodiesParser()
    {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("bodies.json"))
        {
            bodiesDTO = gson.fromJson(reader, BodiesDTO.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            bodiesDTO = null;
        }
    }

    public BodiesDTO getBodiesDTO()
    {
        return bodiesDTO;
    }
}
