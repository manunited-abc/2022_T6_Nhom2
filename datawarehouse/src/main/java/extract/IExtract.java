package extract;

import java.io.IOException;
import java.util.List;

import model.Lottery;

public interface IExtract {
	public List<Lottery> extract() throws IOException;
}
