/*
  Erase Disk #3 - Erase Empty Space on Disk Drive
  Written by: Keith Fenske, http://kwfenske.github.io/
  Wednesday, 23 March 2022
  Java class name: EraseDisk3
  Copyright (c) 2022 by Keith Fenske.  Apache License or GNU GPL.

  EraseDisk is a Java 1.4 graphical (GUI) application to erase and test disk
  drives or flash drives.  Large temporary files are created and filled with
  zeros, ones, or pseudo-random data.  Previously deleted files are
  overwritten.  Existing files are not affected.  This cleans up an old disk
  before it goes in a new location.  Don't trust a new disk until you write
  data, then read to confirm.  One complete test is usually enough.  (Repeated
  testing may degrade flash drives.)  Use this program as follows:

   1. If you want to erase an entire disk drive or partition (including all
      files), then first "format" or "initialize" the drive according to your
      system's usual procedure.
   2. Otherwise, empty the "Recycle Bin" or "Trash" folder.  This releases
      hidden space still allocated to some deleted files.
   3. Run this program.
   4. Navigate to any folder on the disk drive where temporary files can be
      created.  Files with names similar to "ERASE123.DAT" are assumed to
      belong to this program and will be replaced or deleted without notice.
   5. Decide whether you want the program to write only zeros (one pass) or to
      write three passes with all ones (0xFF bytes), pseudo-random bytes, then
      all zeros (0x00 bytes).
   6. Decide whether you want the program to read verify the written data to
      test if the drive is working correctly.  Skip this step if you intend to
      destroy the disk drive after it is erased.
   7. Click the "Start" button and be patient.
   8. Check that the number of bytes reported by this program agrees with what
      your system says for free space.  (Java doesn't really know.)  Also, Java
      doesn't report hardware errors if the operating system recovers.  Look at
      your system error logs; see Event Viewer on Microsoft Windows.

  EraseDisk is not a "secure erase" program and does not meet the DoD 5220.22-M
  standard for the United States.  Please refer to the following Wikipedia.org
  web page:

      http://en.wikipedia.org/wiki/Data_remanence

  There are many such applications available, some for free, by searching "disk
  wipe" or "secure erase" on Google.com or similar web search engines.  The
  programs that do the best job operate at a low level, requiring direct or
  "raw" access to a disk drive (not allowed for regular users), or running as
  stand-alone programs without a full operating system.  They are sensitive to
  what hardware is supported and often fail when new hardware requires drivers
  that are not included in their programming.  Other programs (such as this
  one) work within a standard file system, which allows them to be run by
  regular users, but prevents them from erasing any part of a drive occupied by
  file directories or boot blocks.  Most people just want to make sure that
  files they deleted are truly gone, and this program is good enough for that.

  Apache License or GNU General Public License
  --------------------------------------------
  EraseDisk3 is free software and has been released under the terms and
  conditions of the Apache License (version 2.0 or later) and/or the GNU
  General Public License (GPL, version 2 or later).  This program is
  distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY,
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE.  See the license(s) for more details.  You should have
  received a copy of the licenses along with this program.  If not, see the
  http://www.apache.org/licenses/ and http://www.gnu.org/licenses/ web pages.

  Frequently Asked Questions (FAQ)
  --------------------------------
  Why is there no read verify when writing zeros or ones?  This feature is
  currently disabled.  Any sequence that repeats the same value can be highly
  manipulated.  Since all zeros are the same, there is no guarantee that data
  is coming from the disk drive, or even the correct location.

  What's so great about pseudo-random data?  It can't be compressed or
  predicted by the operating system or hardware.  The system has no choice but
  to do what it's told.  Generating random numbers is slower.  The most
  time-consuming part is actually the read verify.

  I am using disk compression and this program never ends.  Of course not, not
  when writing ones or zeros or multiple copies of the same byte.  Instead of
  writing a constant value, the system counts the repetitions until it reaches
  a huge number like 9,223,372,036,854,775,807 (the largest signed 64-bit
  integer).  Use pseudo-random data with compressed disk drives or folders.

  Why doesn't the program know the exact amount of free space on a disk?
  Compression and encryption affect the reported space.  Only when the program
  runs out of space does it know that the disk drive is full ... probably.

  Can this program be changed to meet the "secure erase" standard?  No.  First,
  there are multiple standards for securely erasing a disk drive.  Many
  programs that claim this standard in fact ignore special cases which prevent
  them from finishing the job.  Second, the proper way to erase a disk drive is
  to overwrite every data block on the entire disk, including the boot block,
  disk directories, file contents, and bad (redirected) sectors.  This can't be
  done from within an existing file system.

  Does the program support custom data patterns?  Yes, after some editing and
  compilation.  Each write pass can use any byte from 0x00 to 0xFF, or
  pseudo-random data.  Each pass can have a read verify.  Each read verify can
  prompt the user to eject and reinsert removable media before the verify
  begins (for flash drives, floppy disks, etc).  There can be any number of
  passes.

  Graphical Versus Console Application
  ------------------------------------
  The Java command line may contain options for the position and size of the
  application window, and the size of the display font.  See the "-?" option
  for a help summary:

      java  EraseDisk3  -?

  The command line has more options than are visible in the graphical
  interface.  An option such as -u16 or -u18 is recommended because the default
  Java font is too small.

  Restrictions and Limitations
  ----------------------------
  As computers become more protective of their files, it is increasingly
  difficult to be certain that a particular file has actually been deleted.
  (See any feature that offers to restore previous versions of a file.)
  Devices with small or unusual block sizes may not be completely erased.  Some
  systems leave partial file names in old directory entries even after files
  are deleted.  Others store very small files inside the directory structure
  (around 728 bytes or less for NTFS).  The only practical way of removing this
  information is to reformat the disk drive or partition ... and lose all
  files.  An operating system's disk cache may make a read verify meaningless
  if the amount of data written to the disk is less than the physical memory
  size (RAM) on a computer.  Windows 2000/XP/Vista/7 tends to misallocate a few
  clusters when large FAT32 volumes are nearly full or files reach their
  maximum size; these show up later as "lost" single-cluster files in CHKDSK.

  Suggestions for New Features
  ----------------------------
  (1) Comments in this program and its documentation have two spaces between
      sentences.  That is an old tradition from the days of manual typewriters
      (source code being inherently monospaced).  Unfortunately, Java may only
      remove a single space in text boxes with the "word wrap" attribute set.
      Our text strings have one space between sentences, while comments have
      two spaces.  Yes, somewhat schizophrenic.  KF, 2011-11-17.
  (2) Java 6 (1.6) and later have a File.getFreeSpace() method to estimate the
      number of unallocated bytes on a disk partition.  This can be an initial
      value for our <runPassEstMax> global variable.  KF, 2012-08-03.
  (3) There are holes in the verification logic, mostly if files change size
      between writing and reading.  KF, 2014-01-12.
*/

import java.awt.*;                // older Java GUI support
import java.awt.event.*;          // older Java GUI event support
import java.io.*;                 // standard I/O
import java.text.*;               // number formatting
import java.util.regex.*;         // regular expressions
import javax.swing.*;             // newer Java GUI support
import javax.swing.border.*;      // decorative borders

public class EraseDisk3
{
  /* constants */

  static final String COPYRIGHT_NOTICE =
    "Copyright (c) 2022 by Keith Fenske. Apache License or GNU GPL.";
  static final int DEFAULT_HEIGHT = -1; // default window height in pixels
  static final int DEFAULT_LEFT = 50; // default window left position ("x")
  static final int DEFAULT_TOP = 50; // default window top position ("y")
  static final int DEFAULT_WIDTH = -1; // default window width in pixels
  static final String EMPTY_STATUS = " "; // message when no status to display
  static final int EXIT_FAILURE = -1; // incorrect request or errors found
  static final int EXIT_SUCCESS = 1; // request completed successfully
  static final int EXIT_UNKNOWN = 0; // don't know or nothing really done
  static final boolean FAST_RANDOM = true; // if we re-use old random numbers
  static final int MIN_FRAME = 200; // minimum window height or width in pixels
  static final int PAUSE_BUTTON_MNEMONIC = KeyEvent.VK_P;
                                  // need to restore original after "Resume"
  static final String PAUSE_BUTTON_TEXT = "Pause";
  static final String PROGRAM_TITLE =
    "Erase Empty Space on Disk Drive - by: Keith Fenske";
  static final int SMALL_MILLIS = 499; // too close to zero in milliseconds
  static final String SYSTEM_FONT = "Dialog"; // this font is always available
  static final int TIMER_DELAY = 1000; // 1.000 seconds between status updates

  /* Buffer sizes must be powers of two, and they must be in descending order
  from largest to smallest.  The first or preferred size should be bigger than
  the block size or "allocation unit" of all disks on your system.  The second
  size will be used after the first size generates a "disk full" error, as will
  the third and later sizes.  The last size should be equal to the smallest
  block size expected, such as 512 bytes for a floppy disk drive.

  The intention is to write with larger and more efficient buffers for most of
  the file space, switching to smaller buffers for completion.  In the future,
  it may be necessary to add a megabyte size and to delete the smallest size.
  Bigger is not always better or faster.  On a test system, Java 6 for Windows
  XP (32-bit) wrote 256 KB buffers at 81 MB/s to an internal SATA 3Gb/s hard
  disk drive, while 2 MB buffers were slower at 63 MB/s.  Windows 7 (32-bit)
  was faster and more consistent on the same hardware.  A good buffer size for
  everyone else (256 KB) was the worst for Windows 10.

                     Average Write Speed (All Zeros, MB/s)

  ---Version---  -------------------------Buffer Size-------------------------
  Windows  Java   64 KB  128 KB  256 KB  512 KB   1 MB    2 MB    4 MB    8 MB

  WinXP    1.4    73.8    75.3    80.5    51.2    61.0    66.0    72.4    75.7
  WinXP    5.0    74.0    75.0    80.1    50.4    60.6    64.0    70.2    75.7
  WinXP    J6     74.6    75.1    81.0    51.0    61.1    63.2    72.2    75.6

  Win7     J6     77.2    81.2    82.2    82.1    83.6    83.9    84.0    81.3
  Win7     J7     79.3    82.1    83.6    81.5    83.0    84.1    83.9    80.1
  Win7     J8     79.4    83.2    83.5    82.5    82.3    82.9    83.0    81.6

  Win10    J8     79.3    81.4    74.2    79.0    77.9    78.9    76.8    79.8

  Interaction between Java and the operating system can create unexpected
  results, such as pseudo-random data being written faster than all zeros or
  ones, even with extra CPU time needed to generate the data.

  It is possible to use sizes that are not powers of two if (1) the list has
  only one element, or (2) each element divides all preceding elements without
  a remainder ("is a factor of"). */

  static final int[] BUFFER_SIZES = { 0x40000, 0x8000, 0x1000, 0x200 };
                                  // 256 KB, 32 KB, 4 KB, 512 bytes
//static final int[] BUFFER_SIZES = { 0x200000, 0x40000, 0x8000, 0x1000 };
//                                // 2 MB, 256 KB, 32 KB, 4 KB

  /* The read verify allows for and reports the occasional error, so long as
  there aren't too many, because it's not unusual to see one bad bit/byte per
  gigabyte for USB flash drives.  When more than ERROR_LIMIT bytes are wrong,
  the verify stops for one temporary file and proceeds with the next, if any.
  One error is "forgiven" (subtracted) after ERROR_RESET consecutively correct
  bytes, making the average tolerance to be one error per ERROR_RESET bytes.
  If entire disk blocks are bad, this program gives up quickly; Java provides
  no physical device information that can be used to compensate. */

  static final long ERROR_LIMIT = 5; // maximum number of recent error bytes
  static final long ERROR_RESET = 123456789; // forgive one error in ... bytes

  /* We place a limit on the number of temporary files created, to prevent this
  program from wasting space in directories.  File systems can be slow if there
  are too many files in one directory.  A thousand files is more than enough
  for FAT32 volumes up to 2 TB having a maximum of 4 GB per file.  Newer disk
  formats, such as NTFS, allow much bigger files.  The constants here affect
  the createFilename() method, which assumes three or more digits. */

  static final int FILE_COUNT_DEFAULT = 999; // default value if no option given
  static final int FILE_COUNT_LOWER = 1; // minimum legal value as an option
  static final int FILE_COUNT_UPPER = 9999; // maximum legal value as an option

  /* There is an advantage to limiting the maximum size of each temporary file,
  in that read verify restarts (resynchronizes) at the beginning of each file,
  when there is more than one.  A maximum file size should be divisible by all
  buffer sizes; otherwise, files may be up to one buffer too big.  Reducing a
  size may require a larger value for the number of files.

  To make output look pretty, choose a number that is a multiple of the largest
  buffer size, and slightly less than a string of nines in decimal.  Messages
  are better if file sizes are also in easy-to-read multiples of a full
  megabyte, gigabyte, terabyte, or petabyte (marked below with "*").  Buffer
  sizes are shown from one kilobyte (KB or KiB) to one gigabyte (GB), although
  large sizes over 64 MB and very small sizes are impractical.

      999,999 (roughly 1 MB) = 0xF423F
      0xF4000 = 999,424 (multiple 1K*, 2K, 4K, 8K, 16K) = 976 KB
      0xF0000 = 983,040 (multiple 32K, 64K)
      0xE0000 = 917,504 (multiple 128K)

      9,999,999 (roughly 10 MB) = 0x98967F
      0x989400 = 9,999,360 (multiple 1K*) = 9765 KB
      0x989000 = 9,998,336 (multiple 2K, 4K)
      0x988000 = 9,994,240 (multiple 8K, 16K, 32K)
      0x980000 = 9,961,472 (multiple 64K, 128K, 256K, 512K)
      0x900000 = 9,437,184 (multiple 1M*) = 9 MB

      99,999,999 (roughly 100 MB) = 0x5F5E0FF
      0x5F5E000 = 99,999,744 (multiple 4K, 8K)
      0x5F5C000 = 99,991,552 (multiple 16K)
      0x5F58000 = 99,975,168 (multiple 32K)
      0x5F50000 = 99,942,400 (multiple 64K)
      0x5F40000 = 99,876,864 (multiple 128K, 256K)
      0x5F00000 = 99,614,720 (multiple 512K, 1M*) = 95 MB
      0x5E00000 = 98,566,144 (multiple 2M)
      0x5C00000 = 96,468,992 (multiple 4M)
      0x5800000 = 92,274,688 (multiple 8M)

      999,999,999 (roughly 1 GB) = 0x3B9AC9FF
      0x3B9AC000 = 999,997,440 (multiple 16K)
      0x3B9A8000 = 999,981,056 (multiple 32K)
      0x3B9A0000 = 999,948,288 (multiple 64K, 128K)
      0x3B980000 = 999,817,216 (multiple 256K, 512K)
      0x3B900000 = 999,292,928 (multiple 1M*) = 953 MB
      0x3B800000 = 998,244,352 (multiple 2M, 4M, 8M)
      0x3B000000 = 989,855,744 (multiple 16M)
      0x3A000000 = 973,078,528 (multiple 32M)
      0x38000000 = 939,524,096 (multiple 64M, 128M)

      9,999,999,999 (roughly 10 GB) = 0x2540BE3FF
      0x2540B0000 = 9,999,941,632 (multiple 64K)
      0x2540A0000 = 9,999,876,096 (multiple 128K)
      0x254080000 = 9,999,745,024 (multiple 256K, 512K)
      0x254000000 = 9,999,220,736 (multiple 1M*, 2M, 4M, 8M, 16M, 32M, 64M) = 9536 MB
      0x250000000 = 9,932,111,872 (multiple 128M, 256M)
      0x240000000 = 9,663,676,416 (multiple 512M, 1G*) = 9 GB

      99,999,999,999 (roughly 100 GB) = 0x174876E7FF
      0x1748740000 = 99,999,809,536 (multiple 256K), default in 2009-2015
      0x1748700000 = 99,999,547,392 (multiple 512K, 1M)
      0x1748600000 = 99,998,498,816 (multiple 2M)
      0x1748400000 = 99,996,401,664 (multiple 4M)
      0x1748000000 = 99,992,207,360 (multiple 8M, 16M, 32M, 64M, 128M)
      0x1740000000 = 99,857,989,632 (multiple 256M, 512M, 1G*) = 93 GB

      999,999,999,999 (roughly 1 TB) = 0xE8D4A50FFF
      0xE8D4A00000 = 999,999,668,224 (multiple 1M, 2M)
      0xE8D4800000 = 999,997,571,072 (multiple 4M, 8M)
      0xE8D4000000 = 999,989,182,464 (multiple 16M, 32M, 64M)
      0xE8D0000000 = 999,922,073,600 (multiple 128M, 256M)
      0xE8C0000000 = 999,653,638,144 (multiple 512M, 1G*) = 931 GB

      9,999,999,999,999 (roughly 10 TB) = 0x9184E729FFF
      0x9184E400000 = 9,999,996,682,240 (multiple 4M)
      0x9184E000000 = 9,999,992,487,936 (multiple 8M, 16M, 32M)
      0x9184C000000 = 9,999,958,933,504 (multiple 64M)
      0x91848000000 = 9,999,891,824,640 (multiple 128M)
      0x91840000000 = 9,999,757,606,912 (multiple 256M, 512M, 1G*) = 9313 GB
      0x90000000000 = 9,895,604,649,984 (multiple 1T*) = 9 TB

      99,999,999,999,999 (roughly 100 TB) = 0x5AF3107A3FFF
      0x5AF310000000 = 99,999,991,988,224 (multiple 16M, 32M, 64M, 128M, 256M)
      0x5AF300000000 = 99,999,723,552,768 (multiple 512M, 1G)
      0x5A0000000000 = 98,956,046,499,840 (multiple 1T*) = 90 TB

      999,999,999,999,999 (roughly 1 PB) = 0x38D7EA4C67FFF
      0x38D7EA4000000 = 999,999,986,991,104 (multiple 64M)
      0x38D7EA0000000 = 999,999,919,882,240 (multiple 128M, 256M, 512M)
      0x38D7E80000000 = 999,999,383,011,328 (multiple 1G)
      0x38D0000000000 = 999,456,069,648,384 (multiple 1T*) = 909 TB

      9,999,999,999,999,999 (roughly 10 PB) = 0x2386F26FC0FFFF
      0x2386F260000000 = 9,999,999,735,693,312 (multiple 256M, 512M)
      0x2386F240000000 = 9,999,999,198,822,400 (multiple 1G)
      0x23860000000000 = 9,998,958,742,994,944 (multiple 1T*) = 9094 TB
      0x20000000000000 = 9,007,199,254,740,992 (multiple 1P*) = 8 PB

      99,999,999,999,999,999 (roughly 100 PB) = 0x16345785D89FFFF
      0x163457840000000 = 99,999,999,504,416,768 (multiple 1G)
      0x163450000000000 = 99,999,483,034,599,424 (multiple 1T)
      0x160000000000000 = 99,079,191,802,150,912 (multiple 1P*) = 88 PB

      999,999,999,999,999,999 (roughly 1 EB) = 0xDE0B6B3A763FFFF
      0xDE0B60000000000 = 999,999,228,392,505,344 (multiple 1T)
      0xDE0000000000000 = 999,799,117,276,250,112 (multiple 1P*) = 888 PB
  */

  static final long FILE_SIZE_DEFAULT = 0x1740000000L;
                                  // 99,857,989,632 bytes (93 GB)
  static final long FILE_SIZE_LOWER = BUFFER_SIZES[0];
                                  // minimum legal value and likely multiple
  static final long FILE_SIZE_UPPER = 0x7FFC000000000000L;
                                  // safe positive 64-bit integer (8191 PB)
  static final long PASS_SIZE_DEFAULT = 0x7FFC000000000000L;
  static final long PASS_SIZE_LOWER = BUFFER_SIZES[0];
  static final long PASS_SIZE_UPPER = 0x7FFC000000000000L;

  /* class variables */

  static JButton cancelButton, erasePanelBack, erasePanelNext, exitButton,
    folderButton, optionPanelBack, optionPanelNext, pauseButton, saveButton,
    startButton, summaryPanelBack, wherePanelNext; // buttons
  static boolean cancelFlag;      // our signal from user to stop processing
  static long clockJobSaved, clockPassSaved;
                                  // elapsed time before pause or prompt
  static long clockJobStart, clockPassStart;
                                  // system millis after pause or prompt
  static boolean debugFlag;       // true if we show debug information
  static boolean deleteFlag;      // true if we delete our temporary files
  static JTextField erasePanelFileAction, erasePanelFileDone,
    erasePanelPassDone, erasePanelPassTime, erasePanelTitle,
    erasePanelTotalTime;          // information fields on "Erase" panel
  static JProgressBar erasePanelFileBar, erasePanelPassBar;
  static EraseDisk3Grid erasePanelGrid; // graphical history of data rates
  static JLabel erasePanelGridScale; // current maximum grid scale value
  static int erasePanelIndex, optionPanelIndex, summaryPanelIndex,
    wherePanelIndex;              // used by Back/Next to navigate tabs
  static JFileChooser fileChooser; // asks for input and output file names
  static NumberFormat formatComma; // formats with commas (digit grouping)
  static NumberFormat formatPointOne; // formats with one decimal digit
  static JFrame mainFrame;        // this application's window if GUI
  static boolean mswinFlag;       // true if running on Microsoft Windows
  static JCheckBox optionCustomWrite, optionOneWrite, optionRandomPrompt,
    optionRandomRead, optionRandomWrite, optionZeroWrite; // checkboxes
  static JTextField optionFileCount, optionFileSize, optionTotalSize;
  static JTextArea outputText;    // generated report while opening files
  static boolean pauseFlag;       // true if we should delay processing
  static Integer pauseWaiter;     // wait on this object for "Pause" button
  static String runFileAction, runPassAction;
                                  // tags saying if reading or writing
  static long runFileBytesDone, runFileEstMax, runPassBytesDone, runPassEstMax,
    runPassPrevBytes, runTotalBytesDone, runTotalErrors;
                                  // running status counters during erase
  static String runFileName;      // current read/write file name, if any
  static double runPassPrevRate;  // previous bytes per second
  static javax.swing.Timer statusTimer; // timer for updating status message
  static JTabbedPane tabbedPane;  // tabbed pane with multiple panels
  static int userFileCount;       // user's maximum number of files
  static long userFileSize;       // user's maximum size of each file
  static File userFolder;         // directory or folder for temporary files
  static String userFolderPath;   // canonical path name for user's folder
  static long userPassSize;       // user's maximum all files, one pass
  static JTextField whereFolderText; // shows name of user's selected folder

/*
  main() method

  We run as a graphical application only.  Set the window layout and then let
  the graphical interface run the show.
*/
  public static void main(String[] args)
  {
    ActionListener action;        // our shared action listener
    boolean borderFlag;           // true if main window has borders, controls
    Font buttonFont;              // font for buttons, labels, status, etc
    Border emptyBorder;           // remove borders around text areas
    String fontName;              // our preferred font name for everything
    int gridBarGap, gridBarWidth; // pixel sizes for columns in bar graph
    int i;                        // index variable
    boolean maxDataRateFlag;      // true if we show maximum observed data rate
    boolean maximizeFlag;         // true if we maximize our main window
    int windowHeight, windowLeft, windowTop, windowWidth;
                                  // position and size for <mainFrame>
    String word;                  // one parameter from command line

    /* Initialize variables used by both console and GUI applications. */

    borderFlag = true;            // by default, window has borders, controls
    buttonFont = null;            // by default, don't use customized font
    cancelFlag = false;           // don't cancel unless user complains
    debugFlag = false;            // by default, don't show debug information
    fontName = null; // "Verdana"; // preferred font name or null for default
    gridBarGap = gridBarWidth = -1; // no pixel sizes for columns in bar graph
    mainFrame = null;             // during setup, there is no GUI window
    maxDataRateFlag = false;      // by default, don't show maximum data rate
    maximizeFlag = false;         // by default, don't maximize our main window
    mswinFlag = System.getProperty("os.name").startsWith("Windows");
    pauseFlag = false;            // don't pause until user clicks button
    userFolder = null;            // no folder yet for temporary files
    userFolderPath = null;        // no path name, because no folder yet
    windowHeight = DEFAULT_HEIGHT; // default window position and size
    windowLeft = DEFAULT_LEFT;
    windowTop = DEFAULT_TOP;
    windowWidth = DEFAULT_WIDTH;

    /* If our preferred font is not available, then use a pre-defined system
    font.  Comment out the <buttonFont> line below to use whatever the current
    Java run-time has for each of the various GUI elements. */

    if ((fontName == null) || (fontName.equals((new Font(fontName, Font.PLAIN,
      16)).getFamily()) == false)) // create font, read back created name
    {
      fontName = SYSTEM_FONT;     // must replace with standard system font
    }
    buttonFont = new Font(fontName, Font.PLAIN, 18); // for buttons, text

    /* Initialize number formatting styles. */

    formatComma = NumberFormat.getInstance(); // current locale
    formatComma.setGroupingUsed(true); // use commas or digit groups

    formatPointOne = NumberFormat.getInstance(); // current locale
    formatPointOne.setGroupingUsed(true); // use commas or digit groups
    formatPointOne.setMaximumFractionDigits(1); // force one decimal digit
    formatPointOne.setMinimumFractionDigits(1);

    /* Check command-line parameters for options. */

    for (i = 0; i < args.length; i ++)
    {
      word = args[i].toLowerCase(); // easier to process if consistent case
      if (word.length() == 0)
      {
        /* Ignore empty parameters, which are more common than you might think,
        when programs are being run from inside scripts (command files). */
      }

      else if (word.equals("?") || word.equals("-?") || word.equals("/?")
        || word.equals("-h") || (mswinFlag && word.equals("/h"))
        || word.equals("-help") || (mswinFlag && word.equals("/help")))
      {
        showHelp();               // show help summary
        System.exit(EXIT_UNKNOWN); // exit application after printing help
      }

      else if (word.equals("-b") || (mswinFlag && word.equals("/b"))
        || word.equals("-b1") || (mswinFlag && word.equals("/b1")))
      {
        borderFlag = true;        // our main window has borders, controls
      }
      else if (word.equals("-b0") || (mswinFlag && word.equals("/b0")))
        borderFlag = false;       // no borders, controls on main window

      else if (word.equals("-d") || (mswinFlag && word.equals("/d"))
        || word.equals("-d1") || (mswinFlag && word.equals("/d1")))
      {
        debugFlag = true;         // yes, show debug information
        System.err.println("main args.length = " + args.length);
        for (int k = 0; k < args.length; k ++)
          System.err.println("main args[" + k + "] = <" + args[k] + ">");
      }
      else if (word.equals("-d0") || (mswinFlag && word.equals("/d0")))
        debugFlag = false;        // don't show debug information

      else if (word.startsWith("-g") || (mswinFlag && word.startsWith("/g")))
      {
        /* This option is followed by a list of two numbers for the pixel width
        and gap spacing of the vertical columns in the bar graph.  We accept
        all values with correct syntax; defaults are silently applied later if
        numbers are out of range. */

        Pattern pattern = Pattern.compile(
          "\\s*\\(\\s*(-?\\d{1,5})\\s*,\\s*(-?\\d{1,5})\\s*\\)\\s*");
        Matcher matcher = pattern.matcher(word.substring(2)); // parse option
        if (matcher.matches())    // if option has proper syntax
        {
          gridBarWidth = Integer.parseInt(matcher.group(1));
          gridBarGap = Integer.parseInt(matcher.group(2));
        }
        else                      // bad syntax or too many digits
        {
          System.err.println("Invalid column width or gap spacing: "
            + args[i]);           // syntax error only, no semantic checking
          showHelp();             // show help summary
          System.exit(EXIT_FAILURE); // exit application after printing help
        }
      }

      else if (word.equals("-r") || (mswinFlag && word.equals("/r"))
        || word.equals("-r1") || (mswinFlag && word.equals("/r1")))
      {
        /* Some people want to see the maximum observed data rate as a number,
        with the bar graph.  Others find this distracting. */

        maxDataRateFlag = true;   // yes, show maximum observed data rate
      }
      else if (word.equals("-r0") || (mswinFlag && word.equals("/r0")))
        maxDataRateFlag = false;  // don't show maximum observed data rate

      else if (word.startsWith("-u") || (mswinFlag && word.startsWith("/u")))
      {
        /* This option is followed by a font point size that will be used for
        buttons, dialogs, labels, etc. */

        int size = -1;            // default value for font point size
        try                       // try to parse remainder as unsigned integer
        {
          size = Integer.parseInt(word.substring(2));
        }
        catch (NumberFormatException nfe) // if not a number or bad syntax
        {
          size = -1;              // set result to an illegal value
        }
        if ((size < 10) || (size > 99))
        {
          System.err.println("Dialog font size must be from 10 to 99: "
            + args[i]);           // notify user of our arbitrary limits
          showHelp();             // show help summary
          System.exit(EXIT_FAILURE); // exit application after printing help
        }
        buttonFont = new Font(fontName, Font.PLAIN, size); // for big sizes
//      buttonFont = new Font(fontName, Font.BOLD, size); // for small sizes
        if (gridBarGap < 0) gridBarGap = (int) Math.round(size * 0.17);
                                  // default pixel size if not already set
        if (gridBarWidth < 0) gridBarWidth = (int) Math.round(size * 0.56);
      }

      else if (word.startsWith("-w") || (mswinFlag && word.startsWith("/w")))
      {
        /* This option is followed by a list of four numbers for the initial
        window position and size.  All values are accepted, but small heights
        or widths will later force the minimum packed size for the layout. */

        Pattern pattern = Pattern.compile(
          "\\s*\\(\\s*(-?\\d{1,5})\\s*,\\s*(-?\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*\\)\\s*");
        Matcher matcher = pattern.matcher(word.substring(2)); // parse option
        if (matcher.matches())    // if option has proper syntax
        {
          windowLeft = Integer.parseInt(matcher.group(1));
          windowTop = Integer.parseInt(matcher.group(2));
          windowWidth = Integer.parseInt(matcher.group(3));
          windowHeight = Integer.parseInt(matcher.group(4));
        }
        else                      // bad syntax or too many digits
        {
          System.err.println("Invalid window position or size: " + args[i]);
          showHelp();             // show help summary
          System.exit(EXIT_FAILURE); // exit application after printing help
        }
      }

      else if (word.equals("-x") || (mswinFlag && word.equals("/x"))
        || word.equals("-x1") || (mswinFlag && word.equals("/x1")))
      {
        maximizeFlag = true;      // yes, maximize our main window
      }
      else if (word.equals("-x0") || (mswinFlag && word.equals("/x0")))
        maximizeFlag = false;     // regular window, don't maximize

      else                        // parameter is not a recognized option
      {
        System.err.println("Option not recognized: " + args[i]);
        showHelp();               // show help summary
        System.exit(EXIT_FAILURE); // exit application after printing help
      }
    }

    /* Open the graphical user interface (GUI).  The standard Java style is the
    most reliable, but you can switch to something closer to the local system,
    if you want. */

//  try
//  {
//    UIManager.setLookAndFeel(
//      UIManager.getCrossPlatformLookAndFeelClassName());
////    UIManager.getSystemLookAndFeelClassName());
//  }
//  catch (Exception ulafe)
//  {
//    System.err.println("Unsupported Java look-and-feel: " + ulafe);
//  }

    /* Initialize shared graphical objects. */

    action = new EraseDisk3User(); // create our shared action listener
    emptyBorder = BorderFactory.createEmptyBorder(); // for removing borders
    fileChooser = new JFileChooser(); // create our shared file chooser
    pauseWaiter = new Integer(0); // wait on this object for "Pause" button
    statusTimer = new javax.swing.Timer(TIMER_DELAY, action);
                                  // update status message on clock ticks only

    /* Create the graphical interface as a series of smaller panels inside
    bigger panels.  The intermediate panel names are of no lasting importance
    and hence are only numbered (panel261, label354, etc). */

    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    if (buttonFont != null) tabbedPane.setFont(buttonFont);

    /* The first panel has a general introduction and selects the location of
    a drive folder where temporary files will be created.  This panel could be
    combined with later options, but is better by itself.

    Java GUI items change their appearance depending upon which options are
    selected.  JTextArea has a white background by default (i.e., is opaque).
    An editable JTextField has a white background, while a non-editable field
    is transparent. */

    wherePanelIndex = tabbedPane.getTabCount(); // used by Back/Next buttons
    JPanel panel210 = new JPanel(new BorderLayout(0, 20));

    JPanel panel230 = new JPanel(); // horizontal row of buttons
    panel230.setLayout(new BoxLayout(panel230, BoxLayout.X_AXIS));

    folderButton = new JButton("Folder...");
    folderButton.addActionListener(action);
    if (buttonFont != null) folderButton.setFont(buttonFont);
    folderButton.setMnemonic(KeyEvent.VK_F);
    folderButton.setToolTipText("Select folder for temporary files.");
    panel230.add(folderButton);
    panel230.add(Box.createHorizontalStrut(20));

    whereFolderText = new JTextField("No folder selected.");
    whereFolderText.setBorder(emptyBorder);
    whereFolderText.setEditable(false); // user can't change this text field
    if (buttonFont != null) whereFolderText.setFont(buttonFont);
//  whereFolderText.setOpaque(false); // transparent background, not white
    panel230.add(whereFolderText);
    panel230.add(Box.createHorizontalStrut(20));

    wherePanelNext = new JButton("Next >");
    wherePanelNext.addActionListener(action);
    if (buttonFont != null) wherePanelNext.setFont(buttonFont);
    wherePanelNext.setMnemonic(KeyEvent.VK_N);
    panel230.add(wherePanelNext);
    panel210.add(panel230, BorderLayout.NORTH);

    JTextArea text250 = new JTextArea(10, 20);
                                  // nominal size to avoid scroll bars
    text250.setEditable(false);   // user can't change this text area
    if (buttonFont != null) text250.setFont(buttonFont);
    text250.setLineWrap(true);    // allow text lines to wrap
    text250.setOpaque(false);     // transparent background, not white
    text250.setText(
      "EraseDisk is a Java 1.4 graphical (GUI) application to erase and test"
      + " disk drives or flash drives. Large temporary files are created and"
      + " filled with zeros, ones, or pseudo-random data. Previously deleted"
      + " files are overwritten. Existing files are not affected. This cleans"
      + " up an old disk before it goes in a new location. Don't trust a new"
      + " disk until you write data, then read to confirm. One complete test"
      + " is usually enough. (Repeated testing may degrade flash drives.)"
      + "\n\nFirst, choose a directory or folder where temporary files can be"
      + " created. Then follow the \"Next\" and \"Start\" buttons."
      + "\n\nCopyright (c) 2022 by Keith Fenske. By using this program, you"
      + " agree to terms and conditions of the Apache License and/or GNU"
      + " General Public License.");
    text250.setWrapStyleWord(true); // wrap at word boundaries
    JScrollPane panel251 = new JScrollPane(text250);
    panel251.setBorder(emptyBorder);
    panel210.add(panel251, BorderLayout.CENTER);

    JPanel panel290 = new JPanel(new BorderLayout(0, 0));
    panel290.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    panel290.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    panel290.add(panel210, BorderLayout.CENTER);
    panel290.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    panel290.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    tabbedPane.addTab(" Where ", panel290);

    /* The second panel has options.  This is the most detailed panel and will
    determine the default or "packed" layout size. */

    optionPanelIndex = tabbedPane.getTabCount(); // used by Back/Next buttons
    JPanel panel310 = new JPanel(new BorderLayout(0, 0));
    JPanel panel320 = new JPanel(); // vertical box for stacking rows
    panel320.setLayout(new BoxLayout(panel320, BoxLayout.Y_AXIS));

    JPanel panel330 = new JPanel(); // horizontal row of buttons
    panel330.setLayout(new BoxLayout(panel330, BoxLayout.X_AXIS));

    optionPanelBack = new JButton("< Back");
    optionPanelBack.addActionListener(action);
    if (buttonFont != null) optionPanelBack.setFont(buttonFont);
    optionPanelBack.setMnemonic(KeyEvent.VK_B);
    panel330.add(optionPanelBack);
    panel330.add(Box.createHorizontalStrut(20));
    panel330.add(Box.createHorizontalGlue());

    optionPanelNext = new JButton("Next >");
    optionPanelNext.addActionListener(action);
    if (buttonFont != null) optionPanelNext.setFont(buttonFont);
    optionPanelNext.setMnemonic(KeyEvent.VK_N);
    panel330.add(optionPanelNext);
    panel320.add(panel330);
    panel320.add(Box.createVerticalStrut(20));

    JPanel panel340 = new JPanel();
    panel340.setLayout(new BoxLayout(panel340, BoxLayout.X_AXIS));
    JLabel label341 = new JLabel("What type of data do you want to write?");
    if (buttonFont != null) label341.setFont(buttonFont);
    panel340.add(label341);
    panel340.add(Box.createHorizontalGlue());
    panel320.add(panel340);
    panel320.add(Box.createVerticalStrut(6));

    JPanel panel342 = new JPanel();
    panel342.setLayout(new BoxLayout(panel342, BoxLayout.X_AXIS));
    optionCustomWrite = new JCheckBox("custom data pattern (0x69 and 0x96)",
      false);                     // special requests, see startErase() method
    if (buttonFont != null) optionCustomWrite.setFont(buttonFont);
    panel342.add(optionCustomWrite);
    panel342.add(Box.createHorizontalGlue());
    panel320.add(panel342);
    panel320.add(Box.createVerticalStrut(2));

    JPanel panel343 = new JPanel();
    panel343.setLayout(new BoxLayout(panel343, BoxLayout.X_AXIS));
    optionOneWrite = new JCheckBox("write all ones (0xFF bytes)", false);
    if (buttonFont != null) optionOneWrite.setFont(buttonFont);
    panel343.add(optionOneWrite);
    panel343.add(Box.createHorizontalGlue());
    panel320.add(panel343);
    panel320.add(Box.createVerticalStrut(2));

    JPanel panel344 = new JPanel();
    panel344.setLayout(new BoxLayout(panel344, BoxLayout.X_AXIS));
    optionRandomWrite = new JCheckBox("pseudo-random data and ", true);
    optionRandomWrite.addActionListener(action);
    if (buttonFont != null) optionRandomWrite.setFont(buttonFont);
    panel344.add(optionRandomWrite);
    optionRandomRead = new JCheckBox("read verify after ", false);
    optionRandomRead.addActionListener(action);
    if (buttonFont != null) optionRandomRead.setFont(buttonFont);
    panel344.add(optionRandomRead);
    optionRandomPrompt = new JCheckBox("prompt", false);
//  optionRandomPrompt.addActionListener(action);
    optionRandomPrompt.setEnabled(false);
    if (buttonFont != null) optionRandomPrompt.setFont(buttonFont);
    panel344.add(optionRandomPrompt);
    panel344.add(Box.createHorizontalGlue());
    panel320.add(panel344);
    panel320.add(Box.createVerticalStrut(2));

    JPanel panel345 = new JPanel();
    panel345.setLayout(new BoxLayout(panel345, BoxLayout.X_AXIS));
    optionZeroWrite = new JCheckBox("write all zeros (0x00 bytes)", false);
    if (buttonFont != null) optionZeroWrite.setFont(buttonFont);
    panel345.add(optionZeroWrite);
    panel345.add(Box.createHorizontalGlue());
    panel320.add(panel345);
    panel320.add(Box.createVerticalStrut(24));

    JPanel panel350 = new JPanel();
    panel350.setLayout(new BoxLayout(panel350, BoxLayout.X_AXIS));
    JLabel label351 = new JLabel("Maximum number of temporary files is ");
    if (buttonFont != null) label351.setFont(buttonFont);
    panel350.add(label351);
    optionFileCount = new JTextField(formatComma.format(FILE_COUNT_DEFAULT));
    optionFileCount.setColumns(5);
    if (buttonFont != null) optionFileCount.setFont(buttonFont);
    optionFileCount.setHorizontalAlignment(JTextField.CENTER);
    optionFileCount.setMargin(new Insets(0, 2, 1, 2));
    optionFileCount.setMaximumSize(optionFileCount.getPreferredSize());
                                  // force a fixed size only after setting
                                  // ... columns, font, margin, etc.
    optionFileCount.setMinimumSize(optionFileCount.getPreferredSize());
    optionFileCount.setOpaque(false); // transparent background, not white
    panel350.add(optionFileCount);
    JLabel label352 = new JLabel(" from "
      + formatComma.format(FILE_COUNT_LOWER) + " to "
      + formatComma.format(FILE_COUNT_UPPER) + ".");
    if (buttonFont != null) label352.setFont(buttonFont);
    panel350.add(label352);
    panel350.add(Box.createHorizontalGlue());
    panel320.add(panel350);
    panel320.add(Box.createVerticalStrut(5));

    JPanel panel360 = new JPanel();
    panel360.setLayout(new BoxLayout(panel360, BoxLayout.X_AXIS));
    JLabel label361 = new JLabel("Maximum size of each temporary file is ");
    if (buttonFont != null) label361.setFont(buttonFont);
    panel360.add(label361);
    optionFileSize = new JTextField(formatByteSize(FILE_SIZE_DEFAULT));
    optionFileSize.setColumns(6);
    if (buttonFont != null) optionFileSize.setFont(buttonFont);
    optionFileSize.setHorizontalAlignment(JTextField.CENTER);
    optionFileSize.setMargin(new Insets(0, 2, 1, 2));
    optionFileSize.setMaximumSize(optionFileSize.getPreferredSize());
    optionFileSize.setMinimumSize(optionFileSize.getPreferredSize());
    optionFileSize.setOpaque(false);
    panel360.add(optionFileSize);
    JLabel label362 = new JLabel(" from " + formatByteSize(FILE_SIZE_LOWER)
      + " to " + formatByteSize(FILE_SIZE_UPPER) + ".");
    if (buttonFont != null) label362.setFont(buttonFont);
    panel360.add(label362);
    panel360.add(Box.createHorizontalGlue());
    panel320.add(panel360);
    panel320.add(Box.createVerticalStrut(5));

    JPanel panel370 = new JPanel();
    panel370.setLayout(new BoxLayout(panel370, BoxLayout.X_AXIS));
    JLabel label371 = new JLabel("Maximum total size for all files is ");
    if (buttonFont != null) label371.setFont(buttonFont);
    panel370.add(label371);
    optionTotalSize = new JTextField(formatByteSize(PASS_SIZE_DEFAULT));
    optionTotalSize.setColumns(7);
    if (buttonFont != null) optionTotalSize.setFont(buttonFont);
    optionTotalSize.setHorizontalAlignment(JTextField.CENTER);
    optionTotalSize.setMargin(new Insets(0, 2, 1, 2));
    optionTotalSize.setMaximumSize(optionTotalSize.getPreferredSize());
    optionTotalSize.setMinimumSize(optionTotalSize.getPreferredSize());
    optionTotalSize.setOpaque(false);
    panel370.add(optionTotalSize);
    JLabel label372 = new JLabel(" from " + formatByteSize(PASS_SIZE_LOWER)
      + " to " + formatByteSize(PASS_SIZE_UPPER) + ".");
    if (buttonFont != null) label372.setFont(buttonFont);
    panel370.add(label372);
    panel370.add(Box.createHorizontalGlue());
    panel320.add(panel370);
    panel320.add(Box.createVerticalStrut(7));
    panel310.add(panel320, BorderLayout.NORTH);

    JTextArea text380 = new JTextArea(3, 48);
                                  // actual size affects "packed" layout
    text380.setEditable(false);   // user can't change this text area
    if (buttonFont != null) text380.setFont(buttonFont);
    text380.setLineWrap(true);    // allow text lines to wrap
    text380.setOpaque(false);     // transparent background, not white
    text380.setText("File sizes that are not a multiple of "
      + formatByteSize(FILE_SIZE_LOWER)
      + " will be rounded up to the next nearest multiple.");
    text380.setWrapStyleWord(true); // wrap at word boundaries
    JScrollPane panel381 = new JScrollPane(text380);
    panel381.setBorder(emptyBorder);
    panel310.add(panel381, BorderLayout.CENTER);

    JPanel panel390 = new JPanel(new BorderLayout(0, 0));
    panel390.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    panel390.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    panel390.add(panel310, BorderLayout.CENTER);
    panel390.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    panel390.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    tabbedPane.addTab(" Options ", panel390);

    /* The third panel has the status for when we actually do the writing.  As
    ugly as this code may be, it was much worse when it was a GridBagLayout. */

    erasePanelIndex = tabbedPane.getTabCount(); // used by Back/Next buttons
    JPanel panel510 = new JPanel(new BorderLayout(0, 0));
    JPanel panel520 = new JPanel(); // vertical box for stacking rows
    panel520.setLayout(new BoxLayout(panel520, BoxLayout.Y_AXIS));

    JPanel panel530 = new JPanel(); // horizontal row of buttons
    panel530.setLayout(new BoxLayout(panel530, BoxLayout.X_AXIS));

    erasePanelBack = new JButton("< Back");
    erasePanelBack.addActionListener(action);
    if (buttonFont != null) erasePanelBack.setFont(buttonFont);
    erasePanelBack.setMnemonic(KeyEvent.VK_B);
    panel530.add(erasePanelBack);
    panel530.add(Box.createHorizontalStrut(20));
    panel530.add(Box.createHorizontalGlue());

    startButton = new JButton("Start");
    startButton.addActionListener(action);
    if (buttonFont != null) startButton.setFont(buttonFont);
    startButton.setMnemonic(KeyEvent.VK_S);
    startButton.setToolTipText("Write, write, write, and maybe read.");
    panel530.add(startButton);
    panel530.add(Box.createHorizontalStrut(20));

    pauseButton = new JButton(PAUSE_BUTTON_TEXT);
    pauseButton.addActionListener(action);
    pauseButton.setEnabled(false);
    if (buttonFont != null) pauseButton.setFont(buttonFont);
    pauseButton.setMnemonic(PAUSE_BUTTON_MNEMONIC);
    pauseButton.setToolTipText("Wait to resume later.");
    panel530.add(pauseButton);
    panel530.add(Box.createHorizontalStrut(20));

    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(action);
    cancelButton.setEnabled(false);
    if (buttonFont != null) cancelButton.setFont(buttonFont);
    cancelButton.setMnemonic(KeyEvent.VK_C);
    cancelButton.setToolTipText("Stop writing, stop everything.");
    panel530.add(cancelButton);
    panel530.add(Box.createHorizontalStrut(20));
    panel530.add(Box.createHorizontalGlue());

    erasePanelNext = new JButton("Next >");
    erasePanelNext.addActionListener(action);
    if (buttonFont != null) erasePanelNext.setFont(buttonFont);
    erasePanelNext.setMnemonic(KeyEvent.VK_N);
    panel530.add(erasePanelNext);
    panel520.add(panel530);
    panel520.add(Box.createVerticalStrut(20));

    erasePanelTitle = new JTextField("Write and maybe read data...");
    erasePanelTitle.setBorder(emptyBorder);
    erasePanelTitle.setEditable(false); // user can't change this text field
    if (buttonFont != null) erasePanelTitle.setFont(buttonFont);
//  erasePanelTitle.setOpaque(false); // transparent background, not white
    panel520.add(erasePanelTitle);
    panel520.add(Box.createVerticalStrut(10));

    erasePanelFileAction = new JTextField(EMPTY_STATUS);
    erasePanelFileAction.setBorder(emptyBorder);
    erasePanelFileAction.setEditable(false);
    if (buttonFont != null) erasePanelFileAction.setFont(buttonFont);
//  erasePanelFileAction.setOpaque(false);
    panel520.add(erasePanelFileAction);
    panel520.add(Box.createVerticalStrut(10));

    erasePanelFileDone = new JTextField(EMPTY_STATUS);
    erasePanelFileDone.setBorder(emptyBorder);
    erasePanelFileDone.setEditable(false);
    if (buttonFont != null) erasePanelFileDone.setFont(buttonFont);
//  erasePanelFileDone.setOpaque(false);
    panel520.add(erasePanelFileDone);
    panel520.add(Box.createVerticalStrut(9));

    erasePanelFileBar = new JProgressBar(0, 100);
    erasePanelFileBar.setBorder(emptyBorder);
    erasePanelFileBar.setBorderPainted(false);
                                  // remove drop shadow on empty border
    if (buttonFont != null) erasePanelFileBar.setFont(buttonFont);
    erasePanelFileBar.setString(EMPTY_STATUS);
    erasePanelFileBar.setStringPainted(true);
    erasePanelFileBar.setValue(0);
    panel520.add(erasePanelFileBar);
    panel520.add(Box.createVerticalStrut(8));

    erasePanelPassTime = new JTextField(EMPTY_STATUS);
    erasePanelPassTime.setBorder(emptyBorder);
    erasePanelPassTime.setEditable(false);
    if (buttonFont != null) erasePanelPassTime.setFont(buttonFont);
//  erasePanelPassTime.setOpaque(false);
    panel520.add(erasePanelPassTime);
    panel520.add(Box.createVerticalStrut(10));

    erasePanelPassDone = new JTextField(EMPTY_STATUS);
    erasePanelPassDone.setBorder(emptyBorder);
    erasePanelPassDone.setEditable(false);
    if (buttonFont != null) erasePanelPassDone.setFont(buttonFont);
//  erasePanelPassDone.setOpaque(false);
    panel520.add(erasePanelPassDone);
    panel520.add(Box.createVerticalStrut(9));

    erasePanelPassBar = new JProgressBar(0, 100);
    erasePanelPassBar.setBorder(emptyBorder);
    erasePanelPassBar.setBorderPainted(false);
    if (buttonFont != null) erasePanelPassBar.setFont(buttonFont);
    erasePanelPassBar.setString(EMPTY_STATUS);
    erasePanelPassBar.setStringPainted(true);
    erasePanelPassBar.setValue(0);
    panel520.add(erasePanelPassBar);
    panel520.add(Box.createVerticalStrut(8));

    erasePanelTotalTime = new JTextField(EMPTY_STATUS);
    erasePanelTotalTime.setBorder(emptyBorder);
    erasePanelTotalTime.setEditable(false);
    if (buttonFont != null) erasePanelTotalTime.setFont(buttonFont);
//  erasePanelTotalTime.setOpaque(false);
    panel520.add(erasePanelTotalTime);
    panel520.add(Box.createVerticalStrut(maxDataRateFlag ? 10 : 20));

    erasePanelGrid = new EraseDisk3Grid(gridBarWidth, gridBarGap);
                                  // graphical history of data rates
    JPanel panel550 = new JPanel(); // maximum observed data rate
    panel550.setLayout(new BoxLayout(panel550, BoxLayout.X_AXIS));
    erasePanelGridScale = new JLabel(EMPTY_STATUS);
    erasePanelGridScale.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
      erasePanelGrid.barColor));  // mark top of grid with drawing color
    if (buttonFont != null) erasePanelGridScale.setFont(buttonFont);
    panel550.add(Box.createHorizontalGlue()); // expand space on left
    panel550.add(erasePanelGridScale);
//  panel550.add(Box.createHorizontalGlue()); // expand space on right
    if (maxDataRateFlag) panel520.add(panel550);

    panel510.add(panel520, BorderLayout.NORTH);
    panel510.add(erasePanelGrid, BorderLayout.CENTER);

    JPanel panel590 = new JPanel(new BorderLayout(0, 0));
    panel590.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    panel590.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    panel590.add(panel510, BorderLayout.CENTER);
    panel590.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    panel590.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    tabbedPane.addTab(" Erase ", panel590);

    /* The fourth panel has the summary and/or error log (mostly text). */

    summaryPanelIndex = tabbedPane.getTabCount(); // used by Back/Next buttons
    JPanel panel710 = new JPanel(new BorderLayout(0, 20));

    JPanel panel730 = new JPanel(); // horizontal row of buttons
    panel730.setLayout(new BoxLayout(panel730, BoxLayout.X_AXIS));

    summaryPanelBack = new JButton("< Back");
    summaryPanelBack.addActionListener(action);
    if (buttonFont != null) summaryPanelBack.setFont(buttonFont);
    summaryPanelBack.setMnemonic(KeyEvent.VK_B);
    panel730.add(summaryPanelBack);
    panel730.add(Box.createHorizontalStrut(20));
    panel730.add(Box.createHorizontalGlue());

    saveButton = new JButton("Save Output...");
    saveButton.addActionListener(action);
    if (buttonFont != null) saveButton.setFont(buttonFont);
    saveButton.setMnemonic(KeyEvent.VK_O);
    saveButton.setToolTipText("Copy output text (below) to a file.");
    panel730.add(saveButton);
    panel730.add(Box.createHorizontalStrut(20));
    panel730.add(Box.createHorizontalGlue());

    exitButton = new JButton("Exit");
    exitButton.addActionListener(action);
    if (buttonFont != null) exitButton.setFont(buttonFont);
    exitButton.setMnemonic(KeyEvent.VK_X);
    exitButton.setToolTipText("Close program, no questions asked.");
    panel730.add(exitButton);
    panel710.add(panel730, BorderLayout.NORTH);

    outputText = new JTextArea(10, 20); // nominal size to avoid scroll bars
    outputText.setEditable(false); // user can't change this text area
    if (buttonFont != null) outputText.setFont(buttonFont);
    outputText.setLineWrap(false); // do not allow text lines to wrap
    outputText.setOpaque(false);  // transparent background, not white
    outputText.setText("Detailed messages go here.\n");
//  outputText.setWrapStyleWord(true); // wrap at word boundaries
    JScrollPane text750 = new JScrollPane(outputText);
    text750.setBorder(emptyBorder);
    panel710.add(text750, BorderLayout.CENTER);

    JPanel panel790 = new JPanel(new BorderLayout(0, 0));
    panel790.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    panel790.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    panel790.add(panel710, BorderLayout.CENTER);
    panel790.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    panel790.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    tabbedPane.addTab(" Summary ", panel790);

    /* Create the main window frame for this application.  We use a border
    layout to add margins around a central area for the drive/option/erase
    panels. */

    mainFrame = new JFrame(PROGRAM_TITLE);
    JPanel panel910 = (JPanel) mainFrame.getContentPane();
    panel910.setLayout(new BorderLayout(0, 0));
    panel910.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    panel910.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    panel910.add(tabbedPane, BorderLayout.CENTER);
    panel910.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    panel910.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLocation(windowLeft, windowTop); // normal top-left corner
    mainFrame.setUndecorated(! borderFlag); // window borders and controls
    if ((windowHeight < MIN_FRAME) || (windowWidth < MIN_FRAME))
      mainFrame.pack();           // do component layout with minimum size
    else                          // the user has given us a window size
      mainFrame.setSize(windowWidth, windowHeight); // size of normal window
    if (maximizeFlag) mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainFrame.validate();         // recheck application window layout
    mainFrame.setVisible(true);   // and then show application window

    /* Let the graphical interface run the application now. */

    tabbedPane.setSelectedIndex(wherePanelIndex);
                                  // default panel with introduction, folder
    folderButton.requestFocusInWindow();
                                  // give keyboard focus to "Folder" button

  } // end of main() method

// ------------------------------------------------------------------------- //

/*
  adjustRandomOptions() method

  Checkboxes for some random write options are only enabled if their preceding
  option is also enabled and selected: you can't change the "read verify"
  checkbox if the "write" checkbox is not already selected, etc.  This only
  applies when we are idle and not while startErase() is running, because
  startErase() sequentially disables write options as it progresses.

  Yes, I know that the boolean expressions below are stronger than necessary.
*/
  static void adjustRandomOptions()
  {
//  optionRandomWrite.setEnabled(true); // first

    optionRandomRead.setEnabled(optionRandomWrite.isEnabled()
      && optionRandomWrite.isSelected()); // second

    optionRandomPrompt.setEnabled(optionRandomRead.isEnabled()
      && optionRandomRead.isSelected()); // third
  }


/*
  cleanCountSize() method

  As the first step in parsing user input for file counts and sizes, keep only
  digits and letters.  Throw away spaces and punctuation (commas, dots, etc).
*/
  static String cleanCountSize(String input)
  {
    StringBuffer buffer;          // faster than String for multiple appends
    char ch;                      // one character from input string
    int i;                        // index variable
    int length;                   // size of input string in characters

    buffer = new StringBuffer();  // allocate empty string buffer for result
    length = input.length();      // get size of input string in characters
    for (i = 0; i < length; i ++) // do all characters in caller's string
    {
      ch = input.charAt(i);       // get one character from input string
      if (Character.isLetterOrDigit(ch)) // Unicode idea of alphanumeric
        buffer.append(ch);        // yes, keep this digit or letter
    }
    return(buffer.toString());    // give caller whatever we could find
  }


/*
  createFilename() method

  Common routine to create a temporary file name given a file number, so that
  all methods create the same file names in the old MS-DOS 8.3 format.

  The code in startEraser() assumes that file numbers are from one (1) to the
  number of files created; however, there is no requirement that file *names*
  match the numbers, so long as the same file number always produces the same
  file name, and each file name is unique.
*/
  static String createFilename(int number)
  {
    String digits = String.valueOf(number); // convert integer to characters
    return("ERASE000".substring(0, (8 - digits.length())) + digits + ".DAT");
  }


/*
  doCancelButton() method

  The user clicked on the "Cancel" button to stop whatever the "Start" button
  is doing.  The word "Cancel" sounds more important than "Stop" and is better
  (more clear to the user) when we also have "Pause" and "Resume" buttons.

  We can't terminate processing immediately, because that is not safe.  So we
  set a boolean flag that is easy (quick) to check.
*/
  static void doCancelButton()
  {
    if (cancelFlag == false)      // if this is the first cancel request
    {
      int reply = JOptionPane.showConfirmDialog(mainFrame,
        "Cancel button clicked during erase.\nDelete temporary files first?");
      if (reply == JOptionPane.NO_OPTION) // user said "no"
        deleteFlag = false;
      else if (reply == JOptionPane.YES_OPTION) // user said "yes"
        deleteFlag = true;
      else                        // CANCEL_OPTION or CLOSED_OPTION
        return;                   // do nothing and ignore "Cancel" button
      putOutput("Cancel button clicked during erase.");
      cancelButton.setEnabled(false); // don't allow multiple cancels
      pauseButton.setEnabled(false); // don't allow pause after cancel
    }
    cancelFlag = true;            // tell other threads that all work stops now
    doPauseClear();               // force end to pause if necessary
  }


/*
  doFolderButton() method

  The user clicked on the "Folder" button to select a directory or folder where
  temporary files can be created.  We only find the folder here; more checking
  is done later after the "Start" button is clicked.
*/
  static void doFolderButton()
  {
    fileChooser.resetChoosableFileFilters(); // remove any existing filters
    fileChooser.setDialogTitle("Select Writeable Drive Folder...");
    fileChooser.setFileHidingEnabled(true); // don't show hidden folders
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setMultiSelectionEnabled(false); // allow only one folder
    if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)
    {
      userFolder = fileChooser.getSelectedFile(); // correct Java object
      try { userFolderPath = userFolder.getCanonicalPath(); }
                                  // get full directory path name, if possible
      catch (IOException ioe) { userFolderPath = userFolder.getPath(); }
                                  // or accept abstract path name otherwise
      whereFolderText.setText(userFolderPath); // display path name on panel
    }
  }


/*
  doPauseButton() method

  The user clicked on the "Pause" button.  This is also the "Resume" button (we
  change the button text).  Set a flag that the working code will recognize and
  wait until we resume (or are cancelled).
*/
  static void doPauseButton()
  {
    if (pauseFlag)                // if button is currently "Resume"
    {
      doPauseClear();             // force end to pause if necessary
    }
    else                          // wait for user, when safe to do so
    {
      pauseButton.setMnemonic(KeyEvent.VK_R); // change key for "Resume"
      pauseButton.setText("Resume"); // change "Pause" to "Resume" button
      pauseFlag = true;           // set flag to enter wait state soon
    }
  }


/*
  doPauseCheck() method

  If the pause flag has been set, wait until the user clicks the "Resume"
  button (or maybe the "Cancel" button).  Otherwise, do nothing, because this
  method is called repeatedly during normal processing.

  This method must be called from a secondary thread, not from the main thread
  that runs the GUI.

  Don't call doPauseClear() here, or else interaction between notify() and
  wait() can be confusing.  Duplicate some code as necessary.
*/
  static void doPauseCheck()
  {
    if (pauseFlag)                // only if "Pause" button clicked
    {
      long stopClock = System.currentTimeMillis(); // time to begin pause
      long hideJob = stopClock - clockJobStart + clockJobSaved;
      long hidePass = stopClock - clockPassStart + clockPassSaved;
//    clockJobSaved = clockPassSaved = 0; // start pause timer from zero
//    clockJobStart = clockPassStart = stopClock;

      synchronized (pauseWaiter)  // enter into a wait state
      {
        try { pauseWaiter.wait(); } catch (InterruptedException ie) { }
      }
      pauseButton.setMnemonic(PAUSE_BUTTON_MNEMONIC); // restore original
      pauseButton.setText(PAUSE_BUTTON_TEXT); // restore original text
      pauseFlag = false;          // no longer waiting for user

      clockJobSaved = hideJob;    // bring back previous elapsed time
      clockPassSaved = hidePass;
      clockJobStart = clockPassStart = System.currentTimeMillis();
                                  // starting time after pause
    }
  } // end of doPauseCheck() method


/*
  doPauseClear() method

  Cleanly terminate the "Pause" button, even if that means resetting things
  that are already correct.
*/
  static void doPauseClear()
  {
    synchronized (pauseWaiter) { pauseWaiter.notifyAll(); }
    pauseButton.setMnemonic(PAUSE_BUTTON_MNEMONIC); // restore original
    pauseButton.setText(PAUSE_BUTTON_TEXT); // restore original text
    pauseFlag = false;            // no longer waiting for user
  }


/*
  doSaveButton() method

  Ask the user for an output file name, create or replace that file, and copy
  the contents of our output text area to that file.  The output file will be
  in the default character set for the system, so if there are special Unicode
  characters in the displayed text (Arabic, Chinese, Eastern European, etc),
  then you are better off copying and pasting the output text directly into a
  Unicode-aware application like Microsoft Word.
*/
  static void doSaveButton()
  {
    FileWriter output;            // output file stream
    File userFile;                // file chosen by the user

    /* Ask the user for an output file name. */

    fileChooser.resetChoosableFileFilters(); // remove any existing filters
    fileChooser.setDialogTitle("Save Output as Text File...");
    fileChooser.setFileHidingEnabled(true); // don't show hidden files
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setMultiSelectionEnabled(false); // allow only one file
    if (fileChooser.showSaveDialog(mainFrame) != JFileChooser.APPROVE_OPTION)
      return;                     // user cancelled file selection dialog box
    userFile = fileChooser.getSelectedFile();

    /* See if we can write to the user's chosen file. */

    if (userFile.isDirectory())   // can't write to directories or folders
    {
      JOptionPane.showMessageDialog(mainFrame, (userFile.getName()
        + " is a directory or folder.\nPlease select a normal file."));
      return;
    }
    else if (userFile.isHidden()) // won't write to hidden (protected) files
    {
      JOptionPane.showMessageDialog(mainFrame, (userFile.getName()
        + " is a hidden or protected file.\nPlease select a normal file."));
      return;
    }
    else if (userFile.isFile() == false) // if file doesn't exist
    {
      /* Maybe we can create a new file by this name.  Do nothing here. */
    }
    else if (userFile.canWrite() == false) // file exists, but is read-only
    {
      JOptionPane.showMessageDialog(mainFrame, (userFile.getName()
        + " is locked or write protected.\nCan't write to this file."));
      return;
    }
    else if (JOptionPane.showConfirmDialog(mainFrame, (userFile.getName()
      + " already exists.\nDo you want to replace this with a new file?"))
      != JOptionPane.YES_OPTION)
    {
      return;                     // user cancelled file replacement dialog
    }

    /* Write lines to output file. */

    try                           // catch file I/O errors
    {
      output = new FileWriter(userFile); // try to open output file
      outputText.write(output);   // couldn't be much easier for writing!
      output.close();             // try to close output file
    }
    catch (IOException ioe)
    {
//    putOutput("Can't write to text file: " + ioe.getMessage());
      JOptionPane.showMessageDialog(mainFrame, ("Can't write to text file:\n"
        + ioe.getMessage()));
    }
  } // end of doSaveButton() method


/*
  doStartButton() method

  The user clicked the "Start" button to begin processing.  Check all options,
  and invoke a separate thread to do the real work.  We can't do much here, or
  else that would block the GUI thread (our thread).
*/
  static void doStartButton()
  {
    Thread runner;                // secondary thread for the real work

    /* There must be a directory or folder where we can create temporary files.
    We check here instead of in doFolderButton() because folders may move or be
    deleted while this program is running. */

    if (userFolder == null)
    {
      JOptionPane.showMessageDialog(mainFrame,
        "Please select a directory or folder where\ntemporary files can be created.");
      tabbedPane.setSelectedIndex(wherePanelIndex); // revisit options
      folderButton.requestFocusInWindow(); // give keyboard focus
      return;
    }
    else if ((userFolder.exists() == false)
      || (userFolder.isDirectory() == false))
    {
      JOptionPane.showMessageDialog(mainFrame,
        "Your folder for temporary files\ndoes not exist or has moved.");
      tabbedPane.setSelectedIndex(wherePanelIndex); // revisit options
      folderButton.requestFocusInWindow(); // give keyboard focus
      return;
    }
    else if (userFolder.canWrite() == false)
    {
      JOptionPane.showMessageDialog(mainFrame,
        "The system does not want this program\nto create files in your selected folder.");
      tabbedPane.setSelectedIndex(wherePanelIndex); // revisit options
      folderButton.requestFocusInWindow(); // give keyboard focus
      return;
    }

    /* At least one write method must be selected. */

    if ((optionCustomWrite.isSelected() || optionOneWrite.isSelected()
      || optionRandomWrite.isSelected() || optionZeroWrite.isSelected())
      == false)
    {
      JOptionPane.showMessageDialog(mainFrame,
        "Please select at least one type of data to write.");
      tabbedPane.setSelectedIndex(optionPanelIndex); // revisit options
      optionRandomWrite.requestFocusInWindow(); // give keyboard focus
      return;
    }

    /* Check the range of the maximum file counts and sizes. */

    try { userFileCount = Integer.parseInt(cleanCountSize(optionFileCount
      .getText())); }             // parse as number only, remove punctuation
    catch (NumberFormatException nfe) { userFileCount = -1; } // error
    if ((userFileCount < FILE_COUNT_LOWER) || (userFileCount > FILE_COUNT_UPPER))
    {
      if (JOptionPane.showConfirmDialog(mainFrame,
        ("The maximum number of temporary files must be from\n"
        + formatComma.format(FILE_COUNT_LOWER) + " to "
        + formatComma.format(FILE_COUNT_UPPER) + ". The default is "
        + formatComma.format(FILE_COUNT_DEFAULT)
        + ". Use the default value?")) != JOptionPane.YES_OPTION)
      {
        tabbedPane.setSelectedIndex(optionPanelIndex); // revisit options
        optionFileCount.requestFocusInWindow(); // give keyboard focus
        optionFileCount.selectAll(); // select all to highlight text
        return;
      }
      userFileCount = FILE_COUNT_DEFAULT; // reset value to default
    }
    optionFileCount.setText(formatComma.format(userFileCount)); // update field

    userFileSize = parseFileSize(cleanCountSize(optionFileSize.getText()));
    if ((userFileSize < FILE_SIZE_LOWER) || (userFileSize > FILE_SIZE_UPPER))
    {
      if (JOptionPane.showConfirmDialog(mainFrame,
        ("The maximum size of each temporary file must be from "
        + formatByteSize(FILE_SIZE_LOWER) + "\nto "
        + formatByteSize(FILE_SIZE_UPPER) + ". The default is "
        + formatByteSize(FILE_SIZE_DEFAULT)
        + ". Use the default value?")) != JOptionPane.YES_OPTION)
      {
        tabbedPane.setSelectedIndex(optionPanelIndex); // revisit options
        optionFileSize.requestFocusInWindow(); // give keyboard focus
        optionFileSize.selectAll(); // select all to highlight text
        return;
      }
      userFileSize = FILE_SIZE_DEFAULT; // reset value to default
    }
    optionFileSize.setText(formatByteSize(userFileSize)); // update field

    userPassSize = parseFileSize(cleanCountSize(optionTotalSize.getText()));
    if ((userPassSize < PASS_SIZE_LOWER) || (userPassSize > PASS_SIZE_UPPER))
    {
      if (JOptionPane.showConfirmDialog(mainFrame,
        ("The total size of all temporary files must be from "
        + formatByteSize(PASS_SIZE_LOWER) + " to\n"
        + formatByteSize(PASS_SIZE_UPPER) + ". The default is "
        + formatByteSize(PASS_SIZE_DEFAULT)
        + ". Use the default value?")) != JOptionPane.YES_OPTION)
      {
        tabbedPane.setSelectedIndex(optionPanelIndex); // revisit options
        optionTotalSize.requestFocusInWindow(); // give keyboard focus
        optionTotalSize.selectAll(); // select all to highlight text
        return;
      }
      userPassSize = PASS_SIZE_DEFAULT; // reset value to default
    }
    optionTotalSize.setText(formatByteSize(userPassSize)); // update field

    /* Start a new secondary thread to do the real work. */

    runner = new Thread(new EraseDisk3User(), "eraseThread");
    runner.setPriority(Thread.MIN_PRIORITY); // use lowest priority in Java VM
    runner.start();               // now run as separate thread to erase disk

  } // end of doStartButton() method


/*
  doStatusTimer() method

  Update the status of the running program at scheduled clock ticks, so that
  numbers and text don't change too quickly.  This method runs in a separate
  timer thread (independent).  We must be careful to work with consistent local
  copies of global variables, and set fields as single, complete strings.

  We call setText() even when the text may not have changed.  Building up a
  string, comparing it, and then setting the text is messy and error prone --
  and the Java run-time does the comparison anyway.
*/
  static void doStatusTimer()
  {
    StringBuffer buffer;          // faster than String for multiple appends
    long fileDone = runFileBytesDone; // changes often so get local copy
    long passDone = runPassBytesDone;
    double percent;               // from zero to a hundred in no time flat
    double rate;                  // current bytes per second
    long totalDone = runTotalBytesDone;

    if (pauseFlag) return;        // do nothing if "Pause" button active
    buffer = new StringBuffer();  // allocate empty string buffer for later

    /* Current file only: action (reading or writing), file name (may include
    file number), bytes per second.  Assume the timer interval is precise to
    make rate calculations easier; the display number is transient anyway. */

    buffer.setLength(0);          // empty any previous contents of buffer
    buffer.append(runFileAction); // tag saying if reading or writing
    if ((runFileName != null) && (runFileName.length() > 0))
    {
      buffer.append(" file ");    // only if we have a current file name
      buffer.append(runFileName);
    }
    rate = (double) (passDone - runPassPrevBytes) * 1000.0 / TIMER_DELAY;
    if (runPassPrevRate < 0.0)    // any previous bytes per second?
      runPassPrevRate = rate;     // no, fix calculation with current rate
    buffer.append(" at ");        // scale into nice units per second
    buffer.append(formatSpeed((rate * 0.7) + (runPassPrevRate * 0.3)));
    buffer.append(".");
    erasePanelFileAction.setText(buffer.toString());
    erasePanelFileAction.select(0, 0); // scroll left if text field too small
    erasePanelGrid.addRate(rate); // append to end of rate history graph
    erasePanelGridScale.setText(formatSpeed(erasePanelGrid.maxFound));
    runPassPrevBytes = passDone;  // remember previously reported amount
    runPassPrevRate = rate;       // remember current bytes per second

    /* Current file only: bytes done, estimated maximum, percent complete. */

    if ((fileDone > 0) || (runFileEstMax > 0))
    {
      buffer.setLength(0);
      buffer.append("File ");
      buffer.append(formatComma.format(fileDone));
      buffer.append(" bytes done");
      if (runFileEstMax > 0)      // if we have an estimated maximum size
      {
        buffer.append(" of ");
        buffer.append(formatComma.format(runFileEstMax));
        buffer.append(" or ");
        percent = 100.0 * (double) fileDone / (double) runFileEstMax;
        percent = Math.min(percent, 100.0); // estimates not always correct
        buffer.append(formatPointOne.format(percent));
        buffer.append(" percent");
        erasePanelFileBar.setString(formatPointOne.format(percent) + " %");
        erasePanelFileBar.setValue((int) Math.round(percent));
      }
      else                        // no estimate for maximum file size
      {
        buffer.append(", unknown maximum");
        erasePanelFileBar.setString(EMPTY_STATUS);
        erasePanelFileBar.setValue(0);
      }
      buffer.append(".");
      erasePanelFileDone.setText(buffer.toString());
      erasePanelFileDone.select(0, 0);
    }
    else                          // no file data worth reporting
    {
      erasePanelFileBar.setString(EMPTY_STATUS);
      erasePanelFileBar.setValue(0);
      erasePanelFileDone.setText(EMPTY_STATUS);
    }

    /* Total for all files in the current "read" or "write" pass: bytes done,
    estimated maximum, percent complete. */

    if ((passDone > fileDone) || (runPassEstMax > runFileEstMax))
    {
      buffer.setLength(0);
      buffer.append("Pass ");
      buffer.append(formatComma.format(passDone));
      buffer.append(" bytes done");
      if (runPassEstMax > 0)      // if we have an estimated maximum size
      {
        buffer.append(" of ");
        buffer.append(formatComma.format(runPassEstMax));
        buffer.append(" or ");
        percent = 100.0 * (double) passDone / (double) runPassEstMax;
        percent = Math.min(percent, 100.0); // estimates not always correct
        buffer.append(formatPointOne.format(percent));
        buffer.append(" percent");
        erasePanelPassBar.setString(formatPointOne.format(percent) + " %");
        erasePanelPassBar.setValue((int) Math.round(percent));
      }
      else                        // no estimate for maximum pass size
      {
        buffer.append(", unknown maximum");
        erasePanelPassBar.setString(EMPTY_STATUS);
        erasePanelPassBar.setValue(0);
      }
      buffer.append(".");
      erasePanelPassDone.setText(buffer.toString());
      erasePanelPassDone.select(0, 0);
    }
    else                          // no pass data worth reporting
    {
      erasePanelPassBar.setString(EMPTY_STATUS);
      erasePanelPassBar.setValue(0);
      erasePanelPassDone.setText(EMPTY_STATUS);
    }

    /* Elapsed time.  The total time for all write methods (all passes) is more
    impressive here than a time for the current "read" or "write" pass.  Users
    want to see bigger numbers when the program runs for a long time! */

    erasePanelPassTime.setText("Elapsed time for this " + runPassAction
      + " pass (all files) is " + formatClock(System.currentTimeMillis()
      - clockPassStart + clockPassSaved) + ".");
    erasePanelPassTime.select(0, 0);

    if ((totalDone > passDone) || (runTotalErrors > 0))
    {
      erasePanelTotalTime.setText(formatClock(System.currentTimeMillis()
        - clockJobStart + clockJobSaved) + " and "
        + formatComma.format(runTotalBytesDone) + " bytes total"
        + ((runTotalErrors > 0) ? ", with errors." : " for all passes."));
      erasePanelTotalTime.select(0, 0);
    }
    else                          // no total data worth reporting
      erasePanelTotalTime.setText(EMPTY_STATUS);

  } // end of doStatusTimer() method


/*
  formatByteSize() method

  Given a non-negative size in bytes, format a string with a metric suffix (KB,
  MB, GB, etc).
*/
  static String formatByteSize(long size)
  {
    long units = size;            // start with bytes, reduce to KB, MB, etc
    String suffix = " B";         // suffix for bytes
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " KB"; }
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " MB"; }
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " GB"; }
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " TB"; }
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " PB"; }
    if ((units > 0) && ((units & 0x3FF) == 0)) { units = units >> 10; suffix = " EB"; }
    return(formatComma.format(units) + suffix); // scaled into units
  }


/*
  formatClock() method

  Given a length of time in milliseconds, format a string with the number of
  days, hours, minutes, and seconds (similar to a digital clock on a wall).
*/
  static String formatClock(long millis)
  {
    int days, hours, minutes, seconds; // calculated pieces of elapsed time
    long time;                    // starts as milliseconds, reduced to days

    time = (millis + 500) / 1000; // round milliseconds to nearest second
    seconds = (int) (time % 60);  // extract current second
    time = time / 60;             // truncate to minutes
    minutes = (int) (time % 60);  // extract current minute
    time = time / 60;             // truncate to hours
    hours = (int) (time % 24);    // extract current hour
    days = (int) (time / 24);     // truncate to days
    return(((days > 0) ? (days + "d ") : "") + hours + "h " + minutes + "m "
      + seconds + "s");           // always show hours, minutes, seconds
  }


/*
  formatHexByte() method

  Format an unsigned byte value as a hexadecimal string with a prefix.  The
  result will have one or two hex digits.  Change this method if you want to
  have exactly two digits.
*/
  static String formatHexByte(byte value)
  {
    return("0x" + Integer.toHexString(0xFF & (int) value).toUpperCase());
  }


/*
  formatHexLong() method

  Format a signed long integer as a hexadecimal string with a prefix.
*/
  static String formatHexLong(long value)
  {
    return("0x" + Long.toHexString(value).toUpperCase());
  }


/*
  formatHours() method

  Given a length of time in milliseconds, format a string with the time in only
  seconds, minutes, hours, or days (whichever is the most expressive).
*/
  static String formatHours(long millis)
  {
    double units = (double) millis / 1000.0; // reduce to seconds
    String suffix = " seconds";   // matching string with those units
    if (units > 99.4) { units = units / 60.0; suffix = " minutes"; }
    if (units > 99.4) { units = units / 60.0; suffix = " hours"; }
    if (units > 99.4) { units = units / 24.0; suffix = " days"; }
    return(formatPointOne.format(units) + suffix); // scaled into units
  }


/*
  formatSpeed() method

  Given a transfer rate or speed in bytes per second, format a string with the
  speed in kilobytes per second, megabytes, gigabytes, or terabytes (whichever
  is the most expressive).
*/
  static String formatSpeed(double speed)
  {
    double units = speed;         // start with bytes per second
    String suffix = " B/s";       // matching string with those units
    if (units > 1999.4) { units = units / 1024.0; suffix = " KB/s"; }
    if (units > 1999.4) { units = units / 1024.0; suffix = " MB/s"; }
    if (units > 1999.4) { units = units / 1024.0; suffix = " GB/s"; }
    if (units > 1999.4) { units = units / 1024.0; suffix = " TB/s"; }
    if (units > 1999.4) { units = units / 1024.0; suffix = " PB/s"; }
    return(formatPointOne.format(units) + suffix); // scaled into units
  }


/*
  parseFileSize() method

  Given a string with a file size and suffix (KB, MB, GB, TB, etc), return the
  size in bytes as a non-negative long integer.  Return -1 if the value is too
  large or the string has poor syntax.  The size must be an integer (zero or
  more) without commas or other digit grouping, followed by an optional suffix.
  If no suffix is given, bytes are assumed.

  If this method is called repeatedly, the compiled regular expression should
  be saved between calls, and the Pattern.CASE_INSENSITIVE option can be used
  instead of String.toLowerCase().
*/
  static long parseFileSize(String input)
  {
    Matcher matcher;              // pattern matcher for given string
    Pattern pattern;              // compiled regular expression
    char prefix;                  // first character of suffix (if any)
    long result;                  // our result (the file size)
    String suffix;                // suffix string (may be null)

    result = -1;                  // assume file size is invalid
    pattern = Pattern.compile(    // compile our regular expression
      "\\s*(\\d+)\\s*(|b|k|kb|kib|m|mb|mib|g|gb|gib|t|tb|tib|p|pb|pib|e|eb|eib)\\s*");
    matcher = pattern.matcher(input.toLowerCase()); // parse given string
    if (matcher.matches())        // if string has proper syntax
    {
      try                         // try to parse digits as unsigned integer
      {
        result = Long.parseLong(matcher.group(1));
      }
      catch (NumberFormatException nfe) // if not a number or bad syntax
      {
        result = -1;              // set result to an illegal value
      }

      /* Scale number with given suffix, if result is in range. */

      if (result < 0)             // was there a non-negative number?
      {
        result = -1;              // no, all negative values are illegal
      }
      else if (((suffix = matcher.group(2)) == null) || (suffix.length() == 0)
        || ((prefix = suffix.charAt(0)) == 'b'))
      {
        /* Size is in bytes.  No scaling or range checking required. */
      }
      else if ((prefix == 'k') && (result <= 0x1FFFFFFFFFFFFFL))
        result = result << 10;    // convert to kilobytes
      else if ((prefix == 'm') && (result <= 0x7FFFFFFFFFFL))
        result = result << 20;    // convert to megabytes
      else if ((prefix == 'g') && (result <= 0x1FFFFFFFFL))
        result = result << 30;    // convert to gigabytes
      else if ((prefix == 't') && (result <= 0x7FFFFFL))
        result = result << 40;    // convert to terabytes
      else if ((prefix == 'p') && (result <= 0x1FFFL))
        result = result << 50;    // convert to petabytes
      else if ((prefix == 'e') && (result <= 0x7L))
        result = result << 60;    // convert to exabytes
      else                        // value with suffix is out of range
        result = -1;              // set result to an illegal value
    }

    return(result);               // return our result (the file size)

  } // end of parseFileSize() method


/*
  prettyPlural() method

  Return a string that formats a number and appends a lowercase "s" to a word
  if the number is plural (not one).  Also provide a more general method that
  accepts both a singular word and a plural word.
*/
  static String prettyPlural(
    long number,                  // number to be formatted
    String singular)              // singular word
  {
    return(prettyPlural(number, singular, (singular + "s")));
  }

  static String prettyPlural(
    long number,                  // number to be formatted
    String singular,              // singular word
    String plural)                // plural word
  {
    final String[] names = {"zero", "one", "two"};
                                  // names for small counting numbers
    String result;                // our converted result

    if ((number >= 0) && (number < names.length))
      result = names[(int) number]; // use names for small counting numbers
    else
      result = formatComma.format(number); // format number with digit grouping

    if (number == 1)              // is the number singular or plural?
      result += " " + singular;   // append singular word
    else
      result += " " + plural;     // append plural word

    return(result);               // give caller our converted string

  } // end of prettyPlural() method


/*
  putOutput() method

  Append a line of text to the end of the output text area.  We add a newline
  character at the end of the line, not the caller.  By forcing all output to
  go through this same method, one complete line at a time, the generated
  output is cleaner and can be redirected.

  The output text area is forced to scroll to the end, after the text line is
  written, by selecting character positions that are much too large (and which
  are allowed by the definition of the JTextComponent.select() method).  This
  is easier and faster than manipulating the scroll bars directly.  However, it
  does cancel any selection that the user might have made, for example, to copy
  text from the output area.
*/
  static void putOutput(String text)
  {
    if (mainFrame == null)        // during setup, there is no GUI window
      System.out.println(text);   // console output goes onto standard output
    else
    {
      outputText.append(text + "\n"); // graphical output goes into text area
      outputText.select(999999999, 999999999); // force scroll to end of text
    }
  }


/*
  showHelp() method

  Show the help summary.  This is a UNIX standard and is expected for all
  console applications, even very simple ones.
*/
  static void showHelp()
  {
    System.err.println();
    System.err.println(PROGRAM_TITLE);
    System.err.println();
    System.err.println("This is a graphical application. You may give options on the command line:");
    System.err.println();
    System.err.println("  -? = -help = show summary of command-line syntax");
//  System.err.println("  -b0 = hide window borders and controls; use full screen if -x1 given");
//  System.err.println("  -b1 = -b = show borders and controls on application window (default)");
    System.err.println("  -d = show debug information (may be verbose)");
    System.err.println("  -g(#,#) = pixel width and gap spacing for vertical columns in bar graph");
    System.err.println("  -r = bar graph shows maximum observed data rate as a number");
//  System.err.println("  -r0 = show bar graph only, hide maximum observed data rate (default)");
//  System.err.println("  -r1 = -r = bar graph shows maximum observed data rate as a number");
    System.err.println("  -u# = font size for buttons, dialogs, etc; default is local system;");
    System.err.println("      example: -u16");
    System.err.println("  -w(#,#,#,#) = normal window position: left, top, width, height;");
    System.err.println("      example: -w(50,50,700,500)");
    System.err.println("  -x = maximize application window; default is normal window");
//  System.err.println("  -x0 = normal or regular window, don't maximize (default)");
//  System.err.println("  -x1 = -x = maximize application window; full screen if -b0 given");
    System.err.println();
    System.err.println(COPYRIGHT_NOTICE);
//  System.err.println();

  } // end of showHelp() method


/*
  sleep() method

  Delay for a given number of milliseconds.  This method must only be called
  from a secondary thread, and should never be called from a main thread that
  runs the GUI, or else you will stall interaction with the user.
*/
  static void sleep(long millis)
  {
    if (millis > 0) try { Thread.sleep(millis); } // only approximate
    catch (InterruptedException ie) { /* do nothing */ }
  }


/*
  startErase() method

  Erase the disk drive.  This method must be called from a secondary thread,
  not from the main thread that runs the GUI.
*/
  static void startErase()
  {
    /* Initialize local and global variables. */

    cancelFlag = false;           // don't cancel unless user complains
    clockJobSaved = clockPassSaved = 0;
                                  // no elapsed time before pause or prompt
    clockJobStart = clockPassStart = System.currentTimeMillis();
                                  // current starting time as system millis
    deleteFlag = true;            // we should delete our temporary files
    pauseFlag = false;            // don't pause until user clicks button
    runFileAction = runPassAction = "*"; // tags saying if reading or writing
    runFileBytesDone = runPassBytesDone = runPassPrevBytes = runTotalBytesDone
      = runTotalErrors = 0;       // reset all status counters to zero
    runFileEstMax = runPassEstMax = -1; // will find actual maximums soon
    runFileName = null;           // don't have a file name yet
    runPassPrevRate = -1.0;       // no previous bytes per second

    /* Enable or disable GUI elements.  Some the user can change even while we
    are running (checkboxes for write methods, etc).  On a large disk, it takes
    hours or days for each pass, and the user may want to add or remove later
    passes.  Once a pass starts, the checkbox for that pass is disabled, and
    this includes any secondary options (read verify, prompt, etc). */

    cancelButton.setEnabled(true);
    erasePanelGrid.clearHistory();
    erasePanelGridScale.setText(EMPTY_STATUS);
    folderButton.setEnabled(false);
    optionFileCount.setEnabled(false); // prefetched, can't be changed
    optionFileSize.setEnabled(false);
    optionTotalSize.setEnabled(false);
    outputText.setText("");
    pauseButton.setEnabled(true);
    startButton.setEnabled(false);
    statusTimer.start();          // update running status by timer

    putOutput("Erasing in drive folder " + userFolderPath);

    /* Erase the disk one or more times, with an optional verify.  Don't bother
    "verifying" data bytes that all have the same value.  You can, if you want,
    but it's probably a waste of time. */

    optionCustomWrite.setEnabled(false); // too late to change this option
    if (optionCustomWrite.isSelected())
    {
      /* Change this section for custom patterns.  You may have multiple calls
      to startEraser().  Check <cancelFlag> before each call.  The default 0x69
      and 0x96 simply flip half the bits, for no good technical reason. */

      if (cancelFlag == false)
        startEraser("Writing custom pattern 0x69...", false, 0x69, false,
          false);

      if (cancelFlag == false)
        startEraser("Writing custom pattern 0x96...", false, 0x96, false,
          false);
    }

    optionOneWrite.setEnabled(false); // too late to change this option
    if ((cancelFlag == false) && optionOneWrite.isSelected())
    {
      startEraser("Writing all ones (0xFF)...", false, 0xFF, false, false);
    }

    optionRandomPrompt.setEnabled(false); // too late to change these options
    optionRandomRead.setEnabled(false);
    optionRandomWrite.setEnabled(false);
    if ((cancelFlag == false) && optionRandomWrite.isSelected())
    {
      startEraser("Writing pseudo-random data...", true, 0x83,
        optionRandomRead.isSelected(), optionRandomPrompt.isSelected());
    }

    optionZeroWrite.setEnabled(false); // too late to change this option
    if ((cancelFlag == false) && optionZeroWrite.isSelected())
    {
      startEraser("Writing all zeros (0x00)...", false, 0x00, false, false);
    }

    /* Tell the user that we are done.  With long delays while writing to the
    disk, it's not so obvious when we are truly finished. */

    statusTimer.stop();           // stop updating our running status by timer
    sleep(TIMER_DELAY);           // might be in middle of status update
    doStatusTimer();              // one final update (may not be complete)
    tabbedPane.setSelectedIndex(summaryPanelIndex); // switch to summary panel
    if (cancelFlag == false)      // if the user didn't interrupt us
    {
      putOutput("");              // blank line
      if (runTotalErrors != 0)    // bad news (if any) goes at the beginning
        putOutput("There were errors. See previous messages (above).");
      long millis = System.currentTimeMillis() - clockJobStart + clockJobSaved;
                                  // total milliseconds elapsed, all passes
      putOutput("Done in " + formatHours(millis) + " (" + formatClock(millis)
        + ").");                  // elapsed time and scaled units
      if (millis > SMALL_MILLIS)  // avoid division close to zero time
      {
        putOutput("Total data was " + formatComma.format(runTotalBytesDone)
          + " bytes at "
          + formatSpeed((double) runTotalBytesDone * 1000.0 / (double) millis)
          + ",");
        putOutput("which includes some overhead (deleting files, etc).");
      }
      else
      {
        putOutput("Total data was " + formatComma.format(runTotalBytesDone)
          + " bytes in the blink of an eye.");
      }
      putOutput("Speeds are not accurate for small time intervals.");
      if (runTotalErrors == 0)    // good news (if any) goes at the end
        putOutput("No errors were detected by this program.");
    }

    /* Revert GUI elements back to normal: enable what was disabled, etc. */

    cancelButton.setEnabled(false);
    folderButton.setEnabled(true);
    optionCustomWrite.setEnabled(true);
    optionFileCount.setEnabled(true);
    optionFileSize.setEnabled(true);
    optionOneWrite.setEnabled(true);
    optionRandomWrite.setEnabled(true); adjustRandomOptions();
    optionTotalSize.setEnabled(true);
    optionZeroWrite.setEnabled(true);
    pauseButton.setEnabled(false);
    startButton.setEnabled(true);

  } // end of startErase() method


/*
  startEraser() method

  This is a helper method to do one pass of writing the disk drive followed by
  an optional read verify.  It returns cleanly even if the user cancels.

  Different file systems have different limits, so don't assume that all empty
  space can be allocated to a single file.  Java throws IOException for almost
  all errors while writing, so we have to guess if this means:

   1. The disk is full; or
   2. A file has reached its maximum size (i.e., 4 GB for FAT32); or
   3. A more serious I/O error.

  Don't rely on message text for an exception because it can vary from system
  to system, and it may not distinguish between the first two cases anyway.
*/
  static void startEraser(
    String description,           // title for "Erase" panel and output text
    boolean randomFlag,           // true if using pseudo-random sequence
    int fillByte,                 // 0x00 to 0xFF data, ignored if random
    boolean verifyFlag,           // true if we read verify after writing
    boolean promptFlag)           // true if we prompt user before verify
  {
    byte compareByte;             // one comparison value during read verify
    byte[] dataBuffer;            // byte buffer writing and comparing files
    int dataIndex;                // current byte or block in data buffer
    int dataLeft;                 // remaining read bytes at <dataIndex>
    long fileBytesRead;           // bytes read from a file, or -1 for error
    boolean fileCompareFail;      // true if any compare fails for one file
    String fileName;              // name of current read/write file
    File fp;                      // File object to write, read, or delete
    int i;                        // index variable
    FileInputStream inp;          // byte input stream for reading one file
    int numFiles;                 // number of files that we have created
    FileOutputStream out;         // byte output stream for writing one file
    long passCorrectBytes;        // correct bytes for read verify (all files)
    long passTimeUsed;            // elapsed time in millis for read/write pass
    boolean passVerifyFail;       // true if compare fails any bytes, any file
    long randomBase;              // start all random sequences from same base
    java.util.Random randomGen;   // fancy pseudo-random number generator
    byte[] readBuffer;            // data bytes read from file during verify
    long recentCorrect, recentErrors; // recent good, bad verify bytes one file
    long savedWriteBytes;         // total number of bytes expected read verify
    int sizeEntry;                // index of current entry in buffer sizes
    int sizeLimit;                // first and assumed maximum buffer size
    int thisFile;                 // current file number, up to <numFiles>
    int thisSize;                 // current buffer size that we are using

    /* Begin by ending early if the user has cancelled. */

    if (cancelFlag) return;       // stop if user hit the panic button

    /* Initialize local and global variables. */

    randomBase = System.currentTimeMillis();
                                  // base for any random number sequences
    randomGen = new java.util.Random(); // create random number generator

    /* Clear data fields that we update in the "Erase" panel. */

    erasePanelFileAction.setText(EMPTY_STATUS);
    erasePanelFileBar.setString(EMPTY_STATUS);
    erasePanelFileBar.setValue(0);
    erasePanelFileDone.setText(EMPTY_STATUS);
    erasePanelPassBar.setString(EMPTY_STATUS);
    erasePanelPassBar.setValue(0);
    erasePanelPassDone.setText(EMPTY_STATUS);
    erasePanelPassTime.setText(EMPTY_STATUS);
    erasePanelTitle.setText(description);
    erasePanelTotalTime.setText(EMPTY_STATUS);

    putOutput("");                // blank line
    putOutput(description);       // copy description to output text area

    /* Create and fill a buffer with data for writing files.  The same buffer
    may be used later for comparing (verify), but not for reading.  We don't
    need perfect pseudo-random numbers, so we do something faster: create a
    buffer twice as big, fill with good random data, and later randomly index
    blocks starting somewhere in the first half. */

    sizeLimit = BUFFER_SIZES[0];  // first size assumed to be largest
    if (randomFlag == false)      // constant value for data bytes?
    {
      dataBuffer = new byte[sizeLimit]; // allocate buffer at maximum size
      for (i = 0; i < sizeLimit; i ++)
        dataBuffer[i] = (byte) fillByte; // use same value for entire buffer
    }
    else if (FAST_RANDOM)         // do we re-use old random numbers?
    {
      dataBuffer = new byte[sizeLimit * 2]; // make twice as big as normal
      randomGen.nextBytes(dataBuffer); // fill with random bytes to re-use
    }
    else                          // always generate random numbers (slow)
    {
      dataBuffer = new byte[sizeLimit]; // allocate buffer at maximum size
    }

    /* Keep creating new files until the disk drive is full, or we reach our
    limit on the number of files.  Some file systems such as NTFS store very
    small files inside the file structure (around 728 bytes or less for NTFS).
    While we could create zillions of these little files, erasing them is best
    done by reformatting the disk drive.  We stop after the total size of a
    temporary file is smaller than our maximum/preferred buffer size. */

    clockPassSaved = 0;           // no elapsed time before pause (prompt only
                                  // happens between passes)
    clockPassStart = System.currentTimeMillis();
                                  // current starting time as system millis
    numFiles = 0;                 // no files created yet
    runFileAction = "Writing";    // tag saying if reading or writing
    runFileBytesDone = runPassBytesDone = runPassPrevBytes = 0;
    runFileName = null;           // don't have a file name yet
    runPassAction = "write";
    runPassPrevRate = -1.0;       // no previous bytes per second

    while ((cancelFlag == false) && (numFiles < userFileCount)
      && (runPassBytesDone < userPassSize)) // outer <while> loop
    {
      if (pauseFlag) doPauseCheck(); // wait if user is busy

      /* Try to create a new temporary file. */

      numFiles ++;                // one more temporary file will be created
      fileName = createFilename(numFiles); // formatted name with file number
      fp = new File(userFolder, fileName); // tell Java we want this file
      try { out = new FileOutputStream(fp); } // we do our own buffering
      catch (FileNotFoundException fnfe) // the only documented exception
      {
        numFiles --;              // we failed, so don't count this file
        putOutput(fileName + " - can't create temporary file");
        runTotalErrors ++;        // one more error detected
        break;                    // exit early from outer <while> loop
      }
      if (debugFlag)              // does user want details?
        putOutput(fileName + " - temporary file created");
      runFileName = fileName;     // save name for next update by timer

      /* Keep writing to the file until it's full.  The first few errors will
      be because our buffer is too big, and writing smaller blocks will work.
      Even when switching to smaller blocks, we try to finish the full buffer
      in sequence, to preserve random data that has already been generated. */

      dataIndex = dataLeft = 0;   // flag buffer as needing random refill
      randomGen.setSeed(randomBase + numFiles);
                                  // start each file with a known random seed
                                  // ... that varies slightly per file
      runFileBytesDone = 0;       // no bytes written yet
      sizeEntry = 0;              // index of first entry in buffer size list
      thisSize = BUFFER_SIZES[sizeEntry];
                                  // value of first entry in size list
      if (debugFlag)              // does user want details?
        putOutput(fileName + " - data buffer size is "
          + formatByteSize(thisSize));

      while ((cancelFlag == false) && (runFileBytesDone < userFileSize)
        && (runPassBytesDone < userPassSize)) // inner <while> loop
      {
        if (pauseFlag) doPauseCheck(); // wait if user is busy

        if (dataLeft <= 0)        // need to find next block of data?
        {
          if (randomFlag == false) // constant value for data bytes?
          {
            dataIndex = 0;        // always at beginning for constant data
          }
          else if (FAST_RANDOM)   // do we re-use old random numbers?
          {
            dataIndex = randomGen.nextInt(sizeLimit);
                                  // somewhat random starting index
          }
          else                    // always generate random numbers (slow)
          {
            randomGen.nextBytes(dataBuffer); // fill with new random bytes
            dataIndex = 0;        // start from beginning of data buffer
          }
          dataLeft = sizeLimit;   // how many bytes in each block (piece)
        }

        try                       // can we write some more to the file?
        {
          out.write(dataBuffer, dataIndex, thisSize);
                                  // try to write a block of data bytes
          if (thisSize < sizeLimit) // once we start using smaller sizes
            out.flush();          // force a disk flush after each write
        }
        catch (IOException ioe)   // assume all errors are "disk may be full"
        {
          if (debugFlag)          // does user want details?
            putOutput(fileName + " - " + ioe.getMessage());
          sizeEntry ++;           // index of next entry in buffer size list
          if (sizeEntry >= BUFFER_SIZES.length) // stop if no more sizes
            break;                // exit early from inner <while> loop
          thisSize = BUFFER_SIZES[sizeEntry]; // value of next entry in list
          if (debugFlag)          // does user want details?
            putOutput(fileName + " - buffer size reduced to "
              + formatByteSize(thisSize));
          continue;               // back to beginning of inner <while> loop
        }
        if (debugFlag && (thisSize < sizeLimit)) // does user want details?
          putOutput(fileName + " - successfully wrote "
            + formatByteSize(thisSize));
        dataIndex += thisSize;    // advance to next block or piece thereof
        dataLeft -= thisSize;     // remaning bytes in this full block
        runFileBytesDone += thisSize; // add to bytes done for this file
        runPassBytesDone += thisSize; // bytes done all files, this pass
        runTotalBytesDone += thisSize; // bytes all passes, all data types
      }
      try { out.close(); } catch (IOException ioe) { /* ignore errors */ }

      if (cancelFlag == false)    // if the user didn't interrupt us
      {
        putOutput(fileName + " - " + formatComma.format(runFileBytesDone)
          + " bytes written");
        if (runFileBytesDone < sizeLimit) // disk full if less than one buffer
          break;                  // exit early from outer <while> loop
        if (runFileEstMax < 0)    // have an estimate for maximum file size?
          runFileEstMax = runFileBytesDone; // no, adjust our expectations
      }
    }

    /* Summary after writing all files. */

    if (cancelFlag == false)      // if the user didn't interrupt us
    {
      if ((numFiles >= userFileCount) || (runPassBytesDone >= userPassSize))
      {                           // normally, we never reach these maximums
        putOutput("Temporary file limit reached; erase may not be complete.");
//      runTotalErrors ++;        // one more error detected
      }
      putOutput("Created " + prettyPlural(numFiles, "temporary file")
        + " with " + formatComma.format(runPassBytesDone) + " bytes.");
      if ((runPassEstMax < 0) && (runPassBytesDone > 0)) // have an estimate?
        runPassEstMax = runPassBytesDone; // no, adjust our expectations

      passTimeUsed = System.currentTimeMillis() - clockPassStart
        + clockPassSaved;         // elapsed time for this write pass
      if (passTimeUsed > SMALL_MILLIS) // avoid division close to zero time
        putOutput("Average write speed was " + formatSpeed((double)
          runPassBytesDone * 1000.0 / (double) passTimeUsed) + " over "
          + formatHours(passTimeUsed) + ".");
    }
    savedWriteBytes = runPassBytesDone; // save for later read verify

    /* Read verify the written data, if this option was chosen by the user.
    Most of our effort is spent on tedious byte-by-byte accounting for correct
    and error bytes.

    Java 1.4 has no standard way of invalidating disk caches in hardware or the
    underlying operating system.  If the amount of data written is smaller than
    the physical memory (RAM) on the computer, data that we read may be fetched
    from the cache and not from the disk.  For removable media, one possible
    solution is a pop-up dialog that asks the user to remove (eject) and then
    reinsert the media. */

    if ((cancelFlag == false) && (savedWriteBytes > 0) && verifyFlag)
    {
//    if (pauseFlag) doPauseCheck(); // wait if user is busy

      /* Prompt the user to eject and reinsert any removable media such as a
      floppy disk or USB flash drive.  We save and restore both timers (job,
      pass) so they can synchronize to zero while waiting for the user. */

      if (promptFlag)             // don't really know if media is removable
      {
        long stopClock = System.currentTimeMillis(); // time to begin pause
        long hideJob = stopClock - clockJobStart + clockJobSaved;
        long hidePass = stopClock - clockPassStart + clockPassSaved;
//      clockJobSaved = clockPassSaved = 0; // start pause timer from zero
//      clockJobStart = clockPassStart = stopClock;

        JOptionPane.showMessageDialog(mainFrame,
          ("If your disk is on removable media, then:\n"
          + "1. Remove (eject) the disk normally;\n"
          + "2. Reinsert the disk; and\n"
          + "3. Click the OK button here."));

        clockJobSaved = hideJob;  // bring back previous elapsed time
        clockPassSaved = hidePass;
        clockJobStart = clockPassStart = System.currentTimeMillis();
                                  // starting time after pause
      }
      putOutput("Reading file data to verify...");

      /* Read all files until their end-of-file.  We don't match file sizes to
      the files we wrote above, only the totals. */

      clockPassSaved = 0;         // no elapsed time before pause (prompt only
                                  // happens between passes)
      clockPassStart = System.currentTimeMillis();
                                  // current starting time as system millis
      compareByte = (byte) fillByte; // use same value except for random data
      passCorrectBytes = 0;       // no correct bytes yet (all files)
      passVerifyFail = false;     // no comparison failures yet on read data
      readBuffer = new byte[sizeLimit]; // allocate buffer at maximum size
      runFileAction = "Reading";  // tag saying if reading or writing
      runFileBytesDone = runPassBytesDone = runPassPrevBytes = 0;
      runFileName = null;         // don't have a file name yet
      runPassAction = "read";
      runPassPrevRate = -1.0;     // no previous bytes per second
      thisFile = 1;               // start with the first file we created

      while ((cancelFlag == false) && (thisFile <= numFiles))
                                  // outer <while> loop
      {
        if (pauseFlag) doPauseCheck(); // wait if user is busy

        /* Try to open a previously created temporary file. */

        fileName = createFilename(thisFile); // formatted name with file number
        fp = new File(userFolder, fileName); // tell Java we want this file
        try { inp = new FileInputStream(fp); } // we do our own buffering
        catch (FileNotFoundException fnfe) // the only documented exception
        {
          putOutput(fileName + " - can't read temporary file");
          runTotalErrors ++;      // one more error detected
          break;                  // exit early from outer <while> loop
        }
        if (debugFlag)            // does user want details?
          putOutput(fileName + " - reading temporary file");
        runFileName = fileName;   // save name for next update by timer

        /* Keep reading from the file until end-of-file or an I/O error. */

        fileBytesRead = 0;        // bytes read from a file, or -1 for error
        fileCompareFail = false;  // true if any compare fails for one file
        randomGen.setSeed(randomBase + thisFile);
                                  // start each file with a known random seed
                                  // ... that varies slightly per file
        recentCorrect = recentErrors = 0; // local number of good and bad bytes
        runFileBytesDone = 0;     // no bytes read yet

        while ((cancelFlag == false) && (fileBytesRead >= 0))
                                  // inner <while> loop
        {
          if (pauseFlag) doPauseCheck(); // wait if user is busy

          try { thisSize = inp.read(readBuffer); } // try to read from file
          catch (IOException ioe) // all errors are bad news when reading
          {
            putOutput(fileName + " - " + ioe.getMessage());
            fileBytesRead = -1;   // mark number of bytes read as invalid
            runTotalErrors ++;    // one more error detected
            break;                // exit early from inner <while> loop
          }
          if (thisSize <= 0)      // stop if we reached the end-of-file
            break;                // exit early from inner <while> loop

          /* Compare data read with the correct data.  There is an assumption
          in this code that the Java run-time will return a full read buffer,
          except for the last read at the end of the file.  This only affects
          pseudo-random data. */

          if (randomFlag == false) // constant value for data bytes?
          {
            dataIndex = 0;        // not used, just to keep compiler happy
          }
          else if (FAST_RANDOM)   // do we re-use old random numbers?
          {
            dataIndex = randomGen.nextInt(sizeLimit);
                                  // somewhat random starting index
          }
          else                    // always generate random numbers (slow)
          {
            randomGen.nextBytes(dataBuffer); // fill with new random bytes
            dataIndex = 0;        // start from beginning of data buffer
          }

          for (i = 0; i < thisSize; i ++) // for all input bytes
          {
            /* Most read verify time is spent inside this one <for> statement,
            so keep it as simple as possible for correct data. */

            if (randomFlag)       // are we reading a pseudo-random sequence?
              compareByte = dataBuffer[dataIndex ++]; // get random byte

            if (readBuffer[i] == compareByte) // compare read with expected
            {
              passCorrectBytes ++; // increase total number of correct bytes
              recentCorrect ++;   // one more consecutively correct byte
              if (recentCorrect >= ERROR_RESET) // enough to forgive an error?
              {
                recentCorrect = 0; // clear local counter for number correct
                if (recentErrors > 0) // are there any recent errors?
                  recentErrors --; // yes, reduce local error count by one
              }
            }
            else                  // what we read is not what we wanted
            {
              putOutput(fileName + " - byte at "
                + formatHexLong(fileBytesRead + i) + " is "
                + formatHexByte(readBuffer[i]) + " but should be "
                + formatHexByte(compareByte));
              fileCompareFail = true; // comparison has failed for this file
              passVerifyFail = true; // at least one failure to compare data
              recentCorrect = 0;  // clear counter for local number correct
              recentErrors ++;    // increase local error count by one
              runTotalErrors ++;  // one more error detected
              if (recentErrors >= ERROR_LIMIT) // too many errors too quickly?
              {
                putOutput(fileName + " - too many errors, stopping after "
                  + formatComma.format(fileBytesRead + i + 1) + " bytes");
                fileBytesRead = -1; // mark number of bytes read as invalid
                break;            // exit early from <for> loop
              }
            }
          }
          if (fileBytesRead < 0)  // did something go wrong with comparison?
            break;                // exit early from inner <while> loop
          fileBytesRead += thisSize; // comparison done, add to local total
          runFileBytesDone += thisSize; // add to bytes done for this file
          runPassBytesDone += thisSize; // bytes done all files, this pass
          runTotalBytesDone += thisSize; // bytes all passes, all data types
        }
        try { inp.close(); } catch (IOException ioe) { /* ignore errors */ }

        if ((cancelFlag == false) && (fileBytesRead >= 0)
          && (fileCompareFail == false))
        {
          putOutput(fileName + " - " + formatComma.format(fileBytesRead)
            + " bytes correct");
        }
        thisFile ++;              // now do the next temporary file
      }

      /* Summary after reading all files. */

      if (cancelFlag == false)    // if the user didn't interrupt us
      {
        if ((passCorrectBytes == savedWriteBytes) && (passVerifyFail == false))
        {
          /* If there were no errors, then the following variables all have the
          same value: passCorrectBytes, runPassBytesDone, savedWriteBytes. */

          putOutput("Verified " + prettyPlural(numFiles, "temporary file")
            + " with " + formatComma.format(runPassBytesDone) + " bytes.");
        }
        else                      // we didn't read what we previously wrote
        {
          putOutput("Verify failed with " + formatComma.format(savedWriteBytes)
            + " bytes written but only " + formatComma.format(passCorrectBytes)
            + " bytes correct.");
        }
        passTimeUsed = System.currentTimeMillis() - clockPassStart
          + clockPassSaved;       // elapsed time for this write pass
        if (passTimeUsed > SMALL_MILLIS) // avoid division close to zero time
          putOutput("Average read speed was " + formatSpeed((double)
            runPassBytesDone * 1000.0 / (double) passTimeUsed) + " over "
            + formatHours(passTimeUsed) + ".");
      }
    }

    /* Delete our temporary files, which can take several seconds each for very
    large files.  We ignore most errors.  The "Cancel" button may be the reason
    why we are here, and may also clear <deleteFlag> while we are deleting. */

    if (deleteFlag && (numFiles > 0)) // should we delete our temporary files?
    {
      runFileAction = "Deleting"; // tag normally says if reading or writing
      runFileName = null;         // don't have a file name yet
      thisFile = 0;               // number of files successfully deleted
      for (i = 1; i <= numFiles; i ++) // for each file that we created
      {
        if (pauseFlag) doPauseCheck(); // wait if user is busy
        if (deleteFlag == false) break; // flag can change by "Cancel" button

        fileName = createFilename(i); // formatted name with file number
        fp = new File(userFolder, fileName); // tell Java we want this file
        runFileName = fileName;   // save name for next update by timer
        if (fp.delete())          // try to delete this file
        {
          thisFile ++;            // one more file deleted
          if (debugFlag)          // does user want details?
            putOutput(fileName + " - temporary file deleted");
        }
        else                      // something went wrong
        {
          putOutput(fileName + " - failed to delete file");
          runTotalErrors ++;      // one more error detected
        }
      }
      putOutput("Deleted " + prettyPlural(thisFile, "temporary file") + ".");
    }
  } // end of startEraser() method


/*
  userButton() method

  This method is called by our action listener actionPerformed() to process
  buttons, in the context of the main EraseDisk3 class.
*/
  static void userButton(ActionEvent event)
  {
    Object source = event.getSource(); // where the event came from
    if (source == cancelButton)   // "Cancel" button on "Erase" tab
    {
      doCancelButton();           // more work than we want to do here
    }
    else if (source == erasePanelBack) // "Back" button on "Erase" tab
    {
      tabbedPane.setSelectedIndex(optionPanelIndex);
    }
    else if (source == erasePanelNext) // "Next" button on "Erase" tab
    {
      tabbedPane.setSelectedIndex(summaryPanelIndex);
    }
    else if (source == exitButton) // "Exit" button
    {
      System.exit(0);             // immediate exit from GUI with no status
    }
    else if (source == folderButton) // "Folder" button on "Where" tab
    {
      doFolderButton();           // more work than we want to do here
    }
    else if (source == optionPanelBack) // "Back" button on "Option" tab
    {
      tabbedPane.setSelectedIndex(wherePanelIndex);
    }
    else if (source == optionPanelNext) // "Next" button on "Option" tab
    {
      tabbedPane.setSelectedIndex(erasePanelIndex);
    }
    else if ((source == optionRandomRead) || (source == optionRandomWrite))
    {
      adjustRandomOptions();      // these checkboxes interact
    }
    else if (source == pauseButton) // "Pause"/"Resume" button on "Erase" tab
    {
      doPauseButton();            // more work than we want to do here
    }
    else if (source == saveButton) // "Save Output" button on "Summary" tab
    {
      doSaveButton();             // more work than we want to do here
    }
    else if (source == startButton) // "Start" button on "Erase" tab
    {
      doStartButton();            // more work than we want to do here
    }
    else if (source == statusTimer) // update timer for status message text
    {
      doStatusTimer();            // more work than we want to do here
    }
    else if (source == summaryPanelBack) // "Back" button on "Summary" tab
    {
      tabbedPane.setSelectedIndex(erasePanelIndex);
    }
    else if (source == wherePanelNext) // "Next" button on "Where" tab
    {
      tabbedPane.setSelectedIndex(optionPanelIndex);
    }
    else                          // fault in program logic, not by user
    {
      System.err.println("Error in userButton(): unknown ActionEvent: "
        + event);                 // should never happen, so write on console
    }
  } // end of userButton() method

} // end of EraseDisk3 class

// ------------------------------------------------------------------------- //

/*
  EraseDisk3Grid class

  This class draws a graph or grid of previous data transfer rates as a simple
  bar graph with vertical bars rising from the bottom.

  Almost no Java objects are created or disposed of in this class, making it
  very memory efficient.  A earlier version used a <Vector> of <Double> objects
  to store the data history.  It was slower and more awkward than the current
  circular buffer (array), because a <Vector> requires constant pruning, which
  must be coordinated with the painting, but can't rely on the painting, since
  a program can run while its paint method isn't being called, for example,
  when the program window is minimized.  (Yes, a deliberate run-on sentence.)

  This class as implemented does not protect its instance variables.
*/

class EraseDisk3Grid extends JPanel
{
  /* constants */

  static final int DATA_SIZE = 512; // maximum size of data in history array
                                  // ... and slightly faster if power of two
  static final int GAP_DEFAULT = 3; // default pixel spacing between bars
  static final long serialVersionUID = 0;
                                  // not used, just to keep compiler happy
  static final int WIDTH_DEFAULT = 10; // default pixel width of each bar

  /* instance variables */

  Color barColor;                 // use same color from Java look-and-feel
  int barGap;                     // pixels between bars in graph
  int barWidth;                   // width of vertical bars in pixels
  int dataCount;                  // number of data values in history array
  double[] dataList;              // history of data values, circular buffer
  int dataStart;                  // index of first or oldest data value
  double maxFound;                // maximum data value found recently

  /* class constructor */

  public EraseDisk3Grid(int width, int gap)
  {
    super();                      // initialize our superclass first (JPanel)

    barColor = UIManager.getColor("ProgressBar.foreground");
                                  // use same color from Java look-and-feel
    if (barColor == null) barColor = Color.GRAY; // do we need a default here?

    barGap = gap;                 // pixels between vertical bars in graph
    if ((barGap < 0) || (barGap > 99)) barGap = GAP_DEFAULT;
                                  // silently apply default if out of range

    barWidth = width;             // width of each vertical bar in pixels
    if ((barWidth < 1) || (barWidth > 999)) barWidth = WIDTH_DEFAULT;

    dataCount = dataStart = 0;    // no data values yet in history array
    dataList = new double[DATA_SIZE]; // circular buffer with fixed size
    maxFound = 123456789.0;       // initial value for maximum rate found
  }

  /* add new data rate to history */

  void addRate(double rate)
  {
    if (dataCount < DATA_SIZE)    // if the circular buffer is not full
    {
      dataList[dataCount ++] = rate; // initial fill, starting from empty
    }
    else                          // buffer is full, re-use oldest entry
    {
      dataList[dataStart] = rate; // starting index is the oldest data
      dataStart = (dataStart + 1) % DATA_SIZE; // increment and wrap around
    }
    this.repaint();               // repaint sometime soon
  }

  /* clear display panel and history */

  void clearHistory()
  {
    dataCount = dataStart = 0;    // ignore previous data in history array
    this.repaint();               // repaint sometime soon
  }

  /* paint the display panel */

  protected void paintComponent(Graphics context)
  {
    int dataNext;                 // index of next data value in history
    int i, k;                     // index variables
    int panelColumns;             // number of columns (vertical bars)
    int panelHeight;              // height of this panel in pixels
    int panelOffset;              // left side of current column (pixels)
    int panelWidth;               // width of this panel in pixels
    double recentMax;             // maximum value seen on this call
    double value;                 // one data value from rate history

    super.paintComponent(context); // anything base JPanel wants first

    /* Calculate how many columns (vertical bars) will fit in the width of the
    given panel.  The size of the panel may change each time we are called. */

    panelHeight = this.getHeight(); // height of this panel in pixels
    panelWidth = this.getWidth(); // width of this panel in pixels
    panelColumns = Math.max(1, ((panelWidth + barGap) / (barGap + barWidth)));
                                  // number of vertical bars across panel
    panelColumns = Math.min(dataCount, panelColumns); // limit available data

    /* Draw each column (vertical bar) and record the maximum value found.  You
    may center or right align the bar graph, in the panel width, by calculating
    an initial value for the <panelOffset> variable. */

    context.setColor(barColor);   // flood fill with our choice of color
    dataNext = (dataStart + dataCount - panelColumns) % DATA_SIZE;
                                  // index of first data value we want
    panelOffset = 0;              // start drawing from left side of panel
    recentMax = 1.0;              // as low as we can go and not be zero
    for (i = 0; i < panelColumns; i ++) // for each column or vertical bar
    {
      value = dataList[dataNext]; // get one data value from history
      k = (int) ((value / maxFound) * panelHeight); // column height in pixels
      k = Math.max(2, Math.min(k, panelHeight)); // minimum (base), maximum
      context.fillRect(panelOffset, (panelHeight - k), barWidth, k);

      dataNext = (dataNext + 1) % DATA_SIZE; // index of next data value
      panelOffset += (barGap + barWidth); // left side of next column
      recentMax = Math.max(recentMax, value); // maximum data value found
    }

    /* Maintain a cushion around the maximum, so that the display scale doesn't
    change too often.  When setting a new value for <maxFound>, a multiplier of
    1.00 causes the exact maximum to show in the GUI, while a larger multiplier
    such as 1.02 provides a better cushion.  Users may quote a displayed number
    as the maximum, so that really should be the maximum. */

    if ((recentMax < (maxFound * 0.95)) || (recentMax > maxFound))
      maxFound = Math.max(1.0, (recentMax * 1.00));

  } // end of paintComponent() method

} // end of EraseDisk3Grid class

// ------------------------------------------------------------------------- //

/*
  EraseDisk3User class

  This class listens to input from the user and passes back event parameters to
  a static method in the main class.
*/

class EraseDisk3User implements ActionListener, Runnable
{
  /* empty constructor */

  public EraseDisk3User() { }

  /* button listener, dialog boxes, etc */

  public void actionPerformed(ActionEvent event)
  {
    EraseDisk3.userButton(event);
  }

  /* separate heavy-duty processing thread */

  public void run() { EraseDisk3.startErase(); }

} // end of EraseDisk3User class

/* Copyright (c) 2022 by Keith Fenske.  Apache License or GNU GPL. */
