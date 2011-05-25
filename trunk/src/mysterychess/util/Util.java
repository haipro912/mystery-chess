package mysterychess.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import mysterychess.model.Advisor;
import mysterychess.model.Cannon;
import mysterychess.model.Chariot;
import mysterychess.model.Elephant;
import mysterychess.model.General;
import mysterychess.model.Horse;
import mysterychess.model.Match;
import mysterychess.model.Piece;
import mysterychess.model.PieceName;
import mysterychess.model.Role;
import mysterychess.model.Soldier;
import mysterychess.model.SuperAdvisor;
import mysterychess.model.SuperElephant;
import mysterychess.model.Team;

/**
 *
 * @author Tin Bui-Huy
 */
public class Util {

    private final static String VERSION = "1.9.2";
    private static final String AUTHOR = "Tin Bui-Huy";
    private static final String GRAPHICS_DESIGNER = "Huy Khong-Minh";
    public final static String RMI_SERVER_NAME = "MysteryChessServer";
    /** Socket timeout in milliseconds */
    public final static int CHECK_ALIVE_INTERVAL = 15 * 1000; // 15 seconds
    public static long PIECE_MOVE_EXPIRE_TIME = 2 * 60 * 1000; // 2 minutes
    public static long GAME_EXPIRE_TIME = 20 * 60 * 1000; // 20 minutes
    private final static Map<String, Image> images = new HashMap<String, Image>();
    private final static int PIECE_NUM = 15;
    public final static int MAX_Y = 9;
    public final static int MAX_X = 8;
    public static final Point BLACK_CHARIOT1_DEFAULT_POSITION = new Point(0, 0);
    public static final Point BLACK_HORSE1_DEFAULT_POSITION = new Point(1, 0);
    public static final Point BLACK_ELEPHANT1_DEFAULT_POSITION = new Point(2, 0);
    public static final Point BLACK_ADVISOR1_DEFAULT_POSITION = new Point(3, 0);
    public static final Point BLACK_GENERAL_DEFAULT_POSITION = new Point(4, 0);
    public static final Point BLACK_ADVISOR2_DEFAULT_POSITION = new Point(5, 0);
    public static final Point BLACK_ELEPHANT2_DEFAULT_POSITION = new Point(6, 0);
    public static final Point BLACK_HORSE2_DEFAULT_POSITION = new Point(7, 0);
    public static final Point BLACK_CHARIOT2_DEFAULT_POSITION = new Point(8, 0);
    public static final Point BLACK_CANNON1_DEFAULT_POSITION = new Point(1, 2);
    public static final Point BLACK_CANNON2_DEFAULT_POSITION = new Point(7, 2);
    public static final Point BLACK_SOLDIER1_DEFAULT_POSITION = new Point(0, 3);
    public static final Point BLACK_SOLDIER2_DEFAULT_POSITION = new Point(2, 3);
    public static final Point BLACK_SOLDIER3_DEFAULT_POSITION = new Point(4, 3);
    public static final Point BLACK_SOLDIER4_DEFAULT_POSITION = new Point(6, 3);
    public static final Point BLACK_SOLDIER5_DEFAULT_POSITION = new Point(8, 3);

    // Init log
    static {
        final String LOG_FILE_NAME = "MysteryChess.log";
        /** This handler is used to publish log records to file */
        FileHandler fileHandler;
        /** This handler is used to publish log records to <tt>System.err</tt> */
        ConsoleHandler consoleHandler = new ConsoleHandler();
        /** Is used to log server related information */
        Logger logger = Logger.getLogger("");
        try {
            String baseDir = System.getProperty("user.dir");
            fileHandler = new FileHandler(baseDir + File.separator + LOG_FILE_NAME);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);

        } catch (Exception ex) {
            System.out.println("Problem happens while creating log file");
        }
    }

    // Load images
    static {
        String[] colors = {"black-", "white-"};
        String[] types = {"mystery-", ""};
        for (String c : colors) {
            for (PieceName name : PieceName.values()) {
                for (String t : types) {
                    if (name == PieceName.general && t.equals("mystery-")) {
                        continue;
                    }
                    loadImage(t + c + name.name() + ".png");
                }
            }
            loadImage(c + "unknown" + ".png");
        }
    }

    public static Collection<Image> getAllImages() {
        return images.values();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getOriginalAuthor() {
        return AUTHOR;
    }

    public static String getGraphicsAuthor() {
        return GRAPHICS_DESIGNER;
    }

    public static void showMessageConcurrently(final Component parent, final String msg) {
        Thread t = new Thread() {

            public void run() {
                JOptionPane.showMessageDialog(parent, msg);
            }
        };
        t.start();
    }

    public static void execute(final Task task) {
        Thread t = new Thread() {

            public void run() {
                try {
                    // TODO write some log
                    Logger.getLogger(Util.class.getName()).info("Executing task "
                            + task.toString());
                    task.perform();
                } catch (Exception ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }

    public static Image getAboutImage() {
        return loadImage("icon.png");
    }

 

    private Util() {
    }

    private static Image loadImage(String fileName) {
        Image img = images.get(fileName);
        if (img == null) {
            String separator = System.getProperty("file.separator");
            //                URL url = ClassLoader.getSystemResource("images" + separator + fileName);
            URL url = Util.class.getResource(fileName);
            if (url != null) {
                img = Toolkit.getDefaultToolkit().getImage(url);
                images.put(fileName, img);
            } else {
                Logger.getLogger("Util").severe("Can not load image file " + fileName);
            }
        }

        return img;
    }

    public static Point transform(Point p) {
        return new Point(MAX_X - p.x, MAX_Y - p.y);
    }

    /**
     * TODO Use java random effectively
     * @return
     */
    public static List<Role> generateRandomRoles() {
        List<Role> source = new ArrayList<Role>();

        source.add(new SuperAdvisor());
        source.add(new SuperAdvisor());
        source.add(new Cannon());
        source.add(new Cannon());
        source.add(new Chariot());
        source.add(new Chariot());
        source.add(new SuperElephant());
        source.add(new SuperElephant());
        source.add(new Horse());
        source.add(new Horse());
        source.add(new Soldier());
        source.add(new Soldier());
        source.add(new Soldier());
        source.add(new Soldier());
        source.add(new Soldier());
        
        // Shuffle twice for sure
        List<Role> result = shuffle(source);
        return shuffle(result);
    }
    
       private static <T> List<T> shuffle(List<T> list) {
        Collections.shuffle(list, new Random(System.nanoTime()));
        List<T> result = new ArrayList<T>();
        do {
            int index = (int) (System.nanoTime() % list.size());
            result.add(list.remove(index));
        } while (list.size() > 1);
        result.add(list.remove(0));
        
        Collections.shuffle(result, new Random(System.nanoTime()));
        return result;
    }

    public static Team createMysteryTopTeam(Match match) {
        Team team = new Team(match, Team.TeamColor.BLACK);
        List<Role> roles = generateRandomRoles();
        List<Piece> pieces = new ArrayList<Piece>();

        Piece p = new Piece(team, BLACK_GENERAL_DEFAULT_POSITION, new General(), new General(), true);
        pieces.add(p);
        int i = 0;

        p = new Piece(team, BLACK_ADVISOR1_DEFAULT_POSITION, new Advisor(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_ADVISOR2_DEFAULT_POSITION, new Advisor(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, BLACK_CANNON1_DEFAULT_POSITION, new Cannon(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_CANNON2_DEFAULT_POSITION, new Cannon(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, BLACK_CHARIOT1_DEFAULT_POSITION, new Chariot(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_CHARIOT2_DEFAULT_POSITION, new Chariot(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, BLACK_ELEPHANT1_DEFAULT_POSITION, new Elephant(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_ELEPHANT2_DEFAULT_POSITION, new Elephant(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, BLACK_HORSE1_DEFAULT_POSITION, new Horse(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_HORSE2_DEFAULT_POSITION, new Horse(), roles.get(i++), false);
        pieces.add(p);


        p = new Piece(team, BLACK_SOLDIER1_DEFAULT_POSITION, new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER2_DEFAULT_POSITION, new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER3_DEFAULT_POSITION, new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER4_DEFAULT_POSITION, new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER5_DEFAULT_POSITION, new Soldier(), roles.get(i++), false);
        pieces.add(p);

        team.setPieces(pieces);
        return team;
    }

    public static Team createMysteryBottomTeam(Match match) {
        Team team = new Team(match, Team.TeamColor.WHITE);
        List<Role> roles = generateRandomRoles();
        List<Piece> pieces = new ArrayList<Piece>();

        Piece p = new Piece(team, transform(BLACK_GENERAL_DEFAULT_POSITION), new General(), new General(), true);
        pieces.add(p);
        int i = 0;

        p = new Piece(team, transform(BLACK_ADVISOR1_DEFAULT_POSITION), new Advisor(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_ADVISOR2_DEFAULT_POSITION), new Advisor(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_CANNON1_DEFAULT_POSITION), new Cannon(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_CANNON2_DEFAULT_POSITION), new Cannon(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_CHARIOT1_DEFAULT_POSITION), new Chariot(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_CHARIOT2_DEFAULT_POSITION), new Chariot(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_ELEPHANT1_DEFAULT_POSITION), new Elephant(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_ELEPHANT2_DEFAULT_POSITION), new Elephant(), roles.get(i++), false);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_HORSE1_DEFAULT_POSITION), new Horse(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_HORSE2_DEFAULT_POSITION), new Horse(), roles.get(i++), false);
        pieces.add(p);


        p = new Piece(team, transform(BLACK_SOLDIER1_DEFAULT_POSITION), new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER2_DEFAULT_POSITION), new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER3_DEFAULT_POSITION), new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER4_DEFAULT_POSITION), new Soldier(), roles.get(i++), false);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER5_DEFAULT_POSITION), new Soldier(), roles.get(i++), false);
        pieces.add(p);

        team.setPieces(pieces);
        return team;
    }

    public static Team createNormalTopTeam(Match match) {
        Team team = new Team(match, Team.TeamColor.BLACK);
        List<Piece> pieces = new ArrayList<Piece>();

        Piece p = new Piece(team, BLACK_GENERAL_DEFAULT_POSITION, new General(), null, true);
        pieces.add(p);
        int i = 0;

        p = new Piece(team, BLACK_ADVISOR1_DEFAULT_POSITION, new Advisor(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_ADVISOR2_DEFAULT_POSITION, new Advisor(), null, true);
        pieces.add(p);

        p = new Piece(team, BLACK_CANNON1_DEFAULT_POSITION, new Cannon(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_CANNON2_DEFAULT_POSITION, new Cannon(), null, true);
        pieces.add(p);

        p = new Piece(team, BLACK_CHARIOT1_DEFAULT_POSITION, new Chariot(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_CHARIOT2_DEFAULT_POSITION, new Chariot(), null, true);
        pieces.add(p);

        p = new Piece(team, BLACK_ELEPHANT1_DEFAULT_POSITION, new Elephant(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_ELEPHANT2_DEFAULT_POSITION, new Elephant(), null, true);
        pieces.add(p);

        p = new Piece(team, BLACK_HORSE1_DEFAULT_POSITION, new Horse(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_HORSE2_DEFAULT_POSITION, new Horse(), null, true);
        pieces.add(p);


        p = new Piece(team, BLACK_SOLDIER1_DEFAULT_POSITION, new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER2_DEFAULT_POSITION, new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER3_DEFAULT_POSITION, new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER4_DEFAULT_POSITION, new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, BLACK_SOLDIER5_DEFAULT_POSITION, new Soldier(), null, true);
        pieces.add(p);

        team.setPieces(pieces);
        return team;
    }

    public static Team createNormalBottomTeam(Match match) {
        Team team = new Team(match, Team.TeamColor.WHITE);
        List<Role> roles = generateRandomRoles();
        List<Piece> pieces = new ArrayList<Piece>();

        Piece p = new Piece(team, transform(BLACK_GENERAL_DEFAULT_POSITION), new General(), null, true);
        pieces.add(p);
        int i = 0;

        p = new Piece(team, transform(BLACK_ADVISOR1_DEFAULT_POSITION), new Advisor(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_ADVISOR2_DEFAULT_POSITION), new Advisor(), null, true);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_CANNON1_DEFAULT_POSITION), new Cannon(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_CANNON2_DEFAULT_POSITION), new Cannon(), null, true);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_CHARIOT1_DEFAULT_POSITION), new Chariot(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_CHARIOT2_DEFAULT_POSITION), new Chariot(), null, true);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_ELEPHANT1_DEFAULT_POSITION), new Elephant(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_ELEPHANT2_DEFAULT_POSITION), new Elephant(), null, true);
        pieces.add(p);

        p = new Piece(team, transform(BLACK_HORSE1_DEFAULT_POSITION), new Horse(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_HORSE2_DEFAULT_POSITION), new Horse(), null, true);
        pieces.add(p);


        p = new Piece(team, transform(BLACK_SOLDIER1_DEFAULT_POSITION), new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER2_DEFAULT_POSITION), new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER3_DEFAULT_POSITION), new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER4_DEFAULT_POSITION), new Soldier(), null, true);
        pieces.add(p);
        p = new Piece(team, transform(BLACK_SOLDIER5_DEFAULT_POSITION), new Soldier(), null, true);
        pieces.add(p);

        team.setPieces(pieces);
        return team;
    }

    public static Image getImage(Piece p) {
        // TODO get filename
        String teamName = p.getTeam().getColor().name().toLowerCase();
        String pieceName = p.getCurrentRole().getName().name().toLowerCase();
        String fileName = "";
        if (!p.isTurnedUp()) {
            fileName = "mystery-" + teamName + "-" + pieceName + ".png";
        } else {
            fileName = teamName + "-" + pieceName + ".png";
        }
        return loadImage(fileName);
    }

    public static Image getRetiredImage(Piece p, boolean captured) {
        String teamName = p.getTeam().getColor().name().toLowerCase();
        String pieceName = "";
        String fileName = "";
        if (captured) {
            pieceName = p.getActualRole().getName().name().toLowerCase();
            if (!p.isTurnedUp()) {
                fileName = "mystery-" + teamName + "-" + pieceName + ".png";
            } else {
                fileName = teamName + "-" + pieceName + ".png";
            }
        } else {
            pieceName = p.getCurrentRole().getName().name().toLowerCase();
            if (!p.isTurnedUp()) {
                fileName = teamName + "-unknown" + ".png";
            } else {
                fileName = teamName + "-" + pieceName + ".png";
            }
        }
        return loadImage(fileName);
    }
}
