package kr.letsnow.crema.file;
import kr.letsnow.crema.file.FileInterface;
import kr.letsnow.crema.file.Exception.ExistSameFileNameException;

public class CremaFileManager implements FileInterface {

	@Override
	public void filesAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void filesForDrive(String driveName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void filesForDirectory(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyFiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutFiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseFiles() throws ExistSameFileNameException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseFile() throws ExistSameFileNameException  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshFilesAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshFilesForDrive(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshFilesForDirectory(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean changeName(String uri) throws ExistSameFileNameException {
		// TODO Auto-generated method stub
		return false;
	}

}
