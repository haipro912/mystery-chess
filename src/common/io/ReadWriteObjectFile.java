package common.io;



import java.io.*;
import java.util.*;

/**
 * @author Bui Huy, Tin
 */

public class ReadWriteObjectFile {
  List<Serializable> list = new ArrayList<Serializable>();
  public void add(Serializable obj) {
    list.add(obj);
  }

  public void add(Serializable[] objs) {
        list.addAll(Arrays.asList(objs));
  }

  public void save(String fileName) {
    try {
      FileOutputStream fout = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fout);
      oos.writeObject(list);
      oos.close();
    } catch (Exception e) { e.printStackTrace();}
  }

  public void load(String fileName) {
    try {
      FileInputStream fin = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fin);
      list =(ArrayList)ois.readObject();
      ois.close();
    } catch (Exception e) { e.printStackTrace();
    } finally {
    }
  }

  public Object[] getObjects() {
    return list.toArray();
  }
}
