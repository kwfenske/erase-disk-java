/*
  Erase Disk #1 - Erase Empty Space on Disk Drive
  Written by: Keith Fenske, http://kwfenske.github.io/
  Thursday, 2 April 2009
  Java class name: EraseDisk1
  Copyright (c) 2009 by Keith Fenske.  GNU General Public License (GPLv3+).

  This is a Java 1.4 graphical (GUI) application to erase and test disk drives
  or flash drives.  Large temporary files are created and filled with zeros,
  ones, or pseudo-random data.  Previously deleted files are overwritten.
  Existing files are not affected.  This cleans up an old disk before it goes
  in a new location.  Don't trust a new disk until you write data, then read to
  confirm.  One complete test is usually enough.  (Repeated testing may degrade
  flash drives.)  Use this program as follows:

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

  GNU General Public License (GPL)
  --------------------------------
  EraseDisk1 is free software: you can redistribute it and/or modify it under
  the terms of the GNU General Public License as published by the Free Software
  Foundation, either version 3 of the License or (at your option) any later
  version.  This program is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY, without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
  more details.

  You should have received a copy of the GNU General Public License along with
  this program.  If not, see the http://www.gnu.org/licenses/ web page.

  Frequently Asked Questions (FAQ)
  --------------------------------
  Why is there no read verify when writing zeros or ones?  This feature is
  currently disabled.  Any sequence that repeats the same value can be highly
  manipulated.  Since all zeros are the same, there is no guarantee that data
  is coming from the disk drive, or even the correct location.

  What's so great about pseudo-random data?  It can't be compressed or
  predicted by the operating system or hardware.  The system has no choice but
  to do what it's told.  Generating random numbers does make this program
  slower, about half the speed of writing zeros.

  I am using disk compression and this program never ends.  Of course not, not
  when writing ones or zeros or multiple copies of the same byte.  Instead of
  writing a constant value, the system counts the repetitions until it reaches
  a huge number like 9,223,372,036,854,775,807 (the largest signed 64-bit
  integer).  Use only pseudo-random data with compressed disk drives or
  folders.

  Why doesn't the program know the exact amount of free space on a disk?
  Compression and encryption affect the reported space.  Only when the program
  runs out of space does it know that the disk drive is full ... probably.

  Can this program be changed to meet the "secure erase" standard?  No.  First,
  there are multiple standards for securely erasing a disk drive.  Many
  programs that claim this standard in fact ignore special cases which prevent
  them from finishing the job.  Second, the only proper way to erase a disk
  drive is to overwrite every data block on the entire disk, including the boot
  block, disk directories, file contents, and bad (redirected) sectors.  This
  can't be done from within an existing file system.

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

      java  EraseDisk1  -?

  The command line has more options than are visible in the graphical
  interface.  An option such as -u14 or -u16 is recommended because the default
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
      KF, 2011-11-17.
  (2) Java 6 (1.6) and later have a File.getFreeSpace() method to estimate the
      number of unallocated bytes on a disk partition.  This is a good initial
      value for our <userBytesTotal> global variable.  KF, 2012-08-03.
  (3) There are holes in the verification logic, mostly if files change size
      between writing and reading.  KF, 2014-01-12.
  (4) There is no "Pause" button.  The only way to prevent this program from
      monopolizing I/O resources is to lower its priority.  KF, 2014-12-27.
*/

import java.awt.*;                // older Java GUI support
import java.awt.event.*;          // older Java GUI event support
import java.io.*;                 // standard I/O
import java.text.*;               // number formatting
import java.util.regex.*;         // regular expressions
import javax.swing.*;             // newer Java GUI support
import javax.swing.border.*;      // decorative borders

public class EraseDisk1
{
  /* constants */

  static final int BYTE_MASK = 0x000000FF; // gets low-order byte from integer
  static final String COPYRIGHT_NOTICE =
    "Copyright (c) 2009 by Keith Fenske.  GNU General Public License (GPLv3+).";
  static final int DEFAULT_HEIGHT = -1; // default window height in pixels
  static final int DEFAULT_LEFT = 50; // default window left position ("x")
  static final int DEFAULT_TOP = 50; // default window top position ("y")
  static final int DEFAULT_WIDTH = -1; // default window width in pixels
  static final String ERASE_TEXT = "Click the Start button to erase the disk"
    + " drive.  If you click the Back or Cancel buttons, you may be asked if"
    + " you want to delete temporary files created by this program.  The Exit"
    + " button doesn't ask any questions.\n\n";
                                  // same message text used in several places
  static final String LICENSE_FILE = "GnuPublicLicense3.txt";
  static final int MIN_FRAME = 200; // minimum window height or width in pixels
  static final String PROGRAM_TITLE =
    "Erase Empty Space on Disk Drive - by: Keith Fenske";
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

  static final int[] BUFFER_SIZE_DEFAULT = { 0x40000, 0x8000, 0x1000, 0x200 };
                                  // 256 KB, 32 KB, 4 KB, 512 bytes
//static final int[] BUFFER_SIZE_DEFAULT = { 0x200000, 0x40000, 0x8000, 0x1000 };
//                                // 2 MB, 256 KB, 32 KB, 4 KB
  static final int BUFFER_SIZE_LOWER = 0x400; // 1 KB
  static final int BUFFER_SIZE_UPPER = 0x2000000; // 32 MB

  /* The read verify allows for and reports the occasional error, so long as
  there aren't too many, because it's not unusual to see one bad bit/byte per
  gigabyte for USB flash drives.  When more than ERROR_LIMIT bytes are wrong,
  the verify stops for one temporary file and proceeds with the next, if any.
  One error is "forgiven" (subtracted) after ERROR_RESET consecutively correct
  bytes, making the average tolerance to be one error per ERROR_RESET bytes.
  If entire disk blocks are bad, this program gives up quickly; Java provides
  no physical device information that can be used to compensate. */

  static final long ERROR_LIMIT = 5; // maximum number of recent error bytes
  static final long ERROR_RESET = 200000000; // forgive one error in ... bytes

  /* We place a limit on the number of temporary files created, to prevent this
  program from wasting space in directories.  File systems can be slow if there
  are too many files in one directory.  A thousand files is more than enough
  for FAT32 volumes up to 2 TB having a maximum of 4 GB per file.  Newer disk
  formats, such as NTFS, allow much bigger files.  The value of <maxFileCount>
  affects the createFilename() method, which assumes three or more digits. */

  static final int MAX_FILE_DEFAULT = 999; // default value if no option given
  static final int MAX_FILE_LOWER = 1; // minimum legal value as an option
  static final int MAX_FILE_UPPER = 9999; // maximum legal value as an option

  /* There is an advantage to limiting the maximum size of each temporary file,
  in that read verify restarts (resynchronizes) at the beginning of each file,
  when there is more than one.  <maxFileSize> should be divisible by all values
  in <bufferSizes>; otherwise, temporary files may be up to one buffer size too
  big.  Reducing <maxFileSize> may require a larger value for <maxFileCount>.

  To make the GUI output look pretty, choose a number that is a multiple of the
  largest buffer size, and slightly less than a string of nines in decimal.
  Console error messages and command-line help are better if sizes are also
  in easy-to-read multiples of a full megabyte, gigabyte, or terabyte (marked
  below with "*").

      9,999,999 (roughly 10 MB) = 0x98967F
      0x988000 = 9,994,240 (multiple 8K, 16K, 32K)
      0x980000 = 9,961,472 (multiple 64K, 128K, 256K, 512K)
      0x900000 = 9,437,184 (multiple 1M*) = 9 MB

      99,999,999 (roughly 100 MB) = 0x5F5E0FF
      0x5F5C000 = 99,991,552 (multiple 16K)
      0x5F58000 = 99,975,168 (multiple 32K)
      0x5F50000 = 99,942,400 (multiple 64K)
      0x5F40000 = 99,876,864 (multiple 128K, 256K)
      0x5F00000 = 99,614,720 (multiple 512K, 1M*) = 95 MB

      999,999,999 (roughly 1 GB) = 0x3B9AC9FF
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
      0x254000000 = 9,999,220,736 (multiple 1M*, 2M) = 9536 MB
      0x250000000 = 9,932,111,872 (multiple 4M, 8M, 16M, 32M, 64M, 128M, 256M)
      0x240000000 = 9,663,676,416 (multiple 512M, 1G*) = 9 GB

      99,999,999,999 (roughly 100 GB) = 0x174876E7FF
      0x1748760000 = 99,999,940,608 (multiple 128K)
      0x1748740000 = 99,999,809,536 (multiple 256K), default in 2009-2015
      0x1748700000 = 99,999,547,392 (multiple 512K, 1M)
      0x1748600000 = 99,998,498,816 (multiple 2M)
      0x1748400000 = 99,996,401,664 (multiple 4M)
      0x1748000000 = 99,992,207,360 (multiple 8M, 16M, 32M, 64M, 128M)
      0x1740000000 = 99,857,989,632 (multiple 256M, 512M, 1G*) = 93 GB

      999,999,999,999 (roughly 1 TB) = 0xE8D4A50FFF
      0xE8D4A40000 = 999,999,930,368 (multiple 256K)
      0xE8D4A00000 = 999,999,668,224 (multiple 512K, 1M, 2M)
      0xE8D4800000 = 999,997,571,072 (multiple 4M, 8M)
      0xE8D4000000 = 999,989,182,464 (multiple 16M, 32M, 64M)
      0xE8D0000000 = 999,922,073,600 (multiple 128M, 256M)
      0xE8C0000000 = 999,653,638,144 (multiple 512M, 1G*) = 931 GB

      9,999,999,999,999 (roughly 10 TB) = 0x9184E729FFF
      0x9184E700000 = 9,999,999,827,968 (multiple 512K, 1M)
      0x9184E600000 = 9,999,998,779,392 (multiple 2M)
      0x9184E400000 = 9,999,996,682,240 (multiple 4M)
      0x9184E000000 = 9,999,992,487,936 (multiple 8M, 16M)
      0x9184C000000 = 9,999,958,933,504 (multiple 32M, 64M)
      0x91848000000 = 9,999,891,824,640 (multiple 128M)
      0x91840000000 = 9,999,757,606,912 (multiple 256M, 512M, 1G*) = 9313 GB
      0x90000000000 = 9,895,604,649,984 (multiple 128G, 256G, 512G, 1T*) = 9 TB

      99,999,999,999,999 (roughly 100 TB) = 0x5AF3107A3FFF
      0x5AF310700000 = 99,999,999,328,256 (multiple 1M)
      0x5AF310600000 = 99,999,998,279,680 (multiple 2M)
      0x5AF310400000 = 99,999,996,182,528 (multiple 4M)
      0x5AF310000000 = 99,999,991,988,224 (multiple 8M, 16M, 32M, 64M, 128M, 256M)
      0x5AF300000000 = 99,999,723,552,768 (multiple 512M, 1G, 2G, 4G)
      0x5A0000000000 = 98,956,046,499,840 (multiple 1T*, 2T) = 90 TB

      999,999,999,999,999 (roughly 1 PB) = 0x38D7EA4C67FFF
      0x38D7EA4C00000 = 999,999,999,574,016 (multiple 2M, 4M)
      0x38D7EA4800000 = 999,999,995,379,712 (multiple 8M)
      0x38D7EA4000000 = 999,999,986,991,104 (multiple 16M, 32M, 64M)
      0x38D7EA0000000 = 999,999,919,882,240 (multiple 128M, 256M, 512M)
      0x38D7E80000000 = 999,999,383,011,328 (multiple 1G, 2G)
      0x38D0000000000 = 999,456,069,648,384 (multiple 512G, 1T*) = 909 TB
  */

  static final long MAX_SIZE_DEFAULT = 0x1740000000L;
                                  // 99,857,989,632 bytes (93 GB)
  static final long MAX_SIZE_LOWER = BUFFER_SIZE_LOWER;
                                  // minimum legal value and likely multiple
  static final long MAX_SIZE_UPPER = 0x7FFC000000000000L;
                                  // safe positive 64-bit integer (8191 PB)

  /* We don't need perfect pseudo-random numbers, so we generate random bytes
  using almost the same algorithm as java.util.Random with no synchronization
  (i.e., not thread safe).  This runs two to three times faster, and changes
  the application from being CPU bound (processor busy) to being I/O bound
  (waiting for I/O completion).  No additional improvement was found when the
  64-bit <long> integers were replaced with 32-bit <int> arithmetic. */

  static final long RANDOM_ADDEND = 0xBL; // 11
  static final long RANDOM_MASK = (1L << 48) - 1; // 48 bits
  static final long RANDOM_MULTIPLIER = 0x5DEECE66DL; // 25,214,903,917

  /* class variables */

  static int[] bufferSizes;       // list of data buffer sizes in order
  static boolean cancelFlag;      // our signal from user to stop processing
  static boolean debugFlag;       // true if we show debug information
  static boolean deleteFlag;      // true if we delete our temporary files
  static JButton driveFolderButton, driveLicenseButton, driveNextButton;
                                  // action buttons on drive page
  static JTextField driveFolderPath, driveFolderStatus; // text on drive page
  static JPanel drivePanel;       // layout panel for drive page
  static File driveSelection;     // user's selected writeable drive folder
  static JButton eraseBackButton, eraseExitButton, eraseStartButton;
  static boolean eraseFlag;       // true if we are currently erasing disk
  static JTextArea eraseOutputText; // scrolling message text for erase page
  static JPanel erasePanel;       // layout panel for erase page
  static JProgressBar eraseProgressBar; // almost like watching paint dry
  static JTextField eraseTimeText; // elapsed time and current speed
  static JFileChooser fileChooser; // asks for input and output file names
  static NumberFormat formatComma; // formats with commas (digit grouping)
  static NumberFormat formatPointOne; // formats with one decimal digit
  static JFrame mainFrame;        // this application's window if GUI
  static Container mainPanel;     // layout panel for main application window
  static int maxFileCount;        // maximum number of temporary files
  static long maxFileSize;        // maximum bytes per temporary file
  static boolean mswinFlag;       // true if running on Microsoft Windows
  static JButton optionBackButton, optionNextButton; // buttons on option page
  static JPanel optionPanel;      // layout panel for option page
  static JCheckBox optionRandomVerify, optionVerifyPrompt, optionWriteCustom,
    optionWriteOnes, optionWriteRandom, optionWriteZeros;
                                  // checkbox selections on option page
  static long randomSeed;         // current seed in pseudo-random generator
  static long startTime;          // erase starting time in milliseconds
  static javax.swing.Timer statusTimer; // timer for updating status message
  static long userBytesDone;      // current number of bytes erased
  static long userBytesPrev;      // previous number of bytes reported
  static double userBytesRate;    // current or previous bytes per second
  static long userBytesTotal;     // total number of bytes erased

/*
  main() method

  We run as a graphical application only.  Set the window layout and then let
  the graphical interface run the show.
*/
  public static void main(String[] args)
  {
    ActionListener action;        // our shared action listener
    Font buttonFont;              // font for buttons, labels, status, etc
    Border emptyBorder;           // remove borders around text areas
    int i;                        // index variable
    boolean maximizeFlag;         // true if we maximize our main window
    int windowHeight, windowLeft, windowTop, windowWidth;
                                  // position and size for <mainFrame>
    String word;                  // one parameter from command line

    /* Initialize variables used by both console and GUI applications. */

    bufferSizes = BUFFER_SIZE_DEFAULT; // list of data buffer sizes in order
    buttonFont = null;            // by default, don't use customized font
    debugFlag = false;            // by default, don't show debug information
    driveSelection = null;        // there is no writeable drive folder yet
    eraseFlag = false;            // we are not actively erasing anything yet
    maxFileCount = MAX_FILE_DEFAULT; // maximum number of temporary files
    maxFileSize = MAX_SIZE_DEFAULT; // maximum bytes per temporary file
    maximizeFlag = false;         // by default, don't maximize our main window
    mswinFlag = System.getProperty("os.name").startsWith("Windows");
    windowHeight = DEFAULT_HEIGHT; // default window position and size
    windowLeft = DEFAULT_LEFT;
    windowTop = DEFAULT_TOP;
    windowWidth = DEFAULT_WIDTH;

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
        System.exit(0);           // exit application after printing help
      }

      else if (word.startsWith("-b") || (mswinFlag && word.startsWith("/b")))
      {
        /* This option is followed by a data buffer size in bytes.  Most sizes
        begin a new list using smaller elements from the default list.  If the
        given size is to be the only size, then simplify this code and create
        an <int> array with exactly one element.

        Bad buffer sizes can cause a lot of trouble, especially if they are too
        small or not powers of two.  We don't do much checking here, as someone
        somewhere may have a special purpose. */

        long size = parseFileSize(word.substring(2)); // parse as long integer
        if ((size < BUFFER_SIZE_LOWER) || (size > BUFFER_SIZE_UPPER))
        {
          System.err.println("Data buffer size in bytes must be from "
            + formatBufferSize(BUFFER_SIZE_LOWER) + "B to "
            + formatBufferSize(BUFFER_SIZE_UPPER) + "B: " + args[i]);
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
        int start = 0;            // assume we can use all default sizes
        while (start < BUFFER_SIZE_DEFAULT.length) // while more default sizes
        {
          if ((size > BUFFER_SIZE_DEFAULT[start]) // where given size is bigger
            && ((size % BUFFER_SIZE_DEFAULT[start]) == 0)) // and a multiple
            break;                // this default size is acceptable
          else                    // given size is smaller or not a multiple
            start ++;             // index of next default size
        }
        bufferSizes = new int[1 + BUFFER_SIZE_DEFAULT.length - start];
        bufferSizes[0] = (int) size; // given size becomes first buffer size
        for (int k = 1; k < bufferSizes.length; k ++) // copy remaining sizes
          bufferSizes[k] = BUFFER_SIZE_DEFAULT[start ++];
      }

      else if (word.equals("-d") || (mswinFlag && word.equals("/d")))
      {
        debugFlag = true;         // show debug information
        System.err.println("main args.length = " + args.length);
        for (int k = 0; k < args.length; k ++)
          System.err.println("main args[" + k + "] = <" + args[k] + ">");
      }

      else if (word.startsWith("-f") || (mswinFlag && word.startsWith("/f")))
      {
        /* This option is followed by a maximum number of temporary files. */

        try                       // try to parse remainder as unsigned integer
        {
          maxFileCount = Integer.parseInt(word.substring(2));
        }
        catch (NumberFormatException nfe) // if not a number or bad syntax
        {
          maxFileCount = -1;      // set result to an illegal value
        }
        if ((maxFileCount < MAX_FILE_LOWER) || (maxFileCount > MAX_FILE_UPPER))
        {
          System.err.println("Maximum number of temporary files must be from "
            + MAX_FILE_LOWER + " to " + MAX_FILE_UPPER + ": " + args[i]);
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
      }

      else if (word.startsWith("-s") || (mswinFlag && word.startsWith("/s")))
      {
        /* This option is followed by a maximum size in bytes for each
        temporary file. */

        maxFileSize = parseFileSize(word.substring(2));
        if ((maxFileSize < MAX_SIZE_LOWER) || (maxFileSize > MAX_SIZE_UPPER))
        {
          System.err.println("Maximum bytes per temporary file must be from "
            + formatBufferSize(MAX_SIZE_LOWER) + "B to "
            + formatBufferSize(MAX_SIZE_UPPER) + "B: " + args[i]);
          showHelp();             // show help summary
          System.exit(-1);        // exit application after printing help
        }
      }

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
          System.exit(-1);        // exit application after printing help
        }
        buttonFont = new Font(SYSTEM_FONT, Font.PLAIN, size); // for big sizes
//      buttonFont = new Font(SYSTEM_FONT, Font.BOLD, size); // for small sizes
      }

      else if (word.startsWith("-w") || (mswinFlag && word.startsWith("/w")))
      {
        /* This option is followed by a list of four numbers for the initial
        window position and size.  All values are accepted, but small heights
        or widths will later force the minimum packed size for the layout. */

        Pattern pattern = Pattern.compile(
          "\\s*\\(\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*\\)\\s*");
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
          System.exit(-1);        // exit application after printing help
        }
      }

      else if (word.equals("-x") || (mswinFlag && word.equals("/x")))
        maximizeFlag = true;      // true if we maximize our main window

      else                        // parameter is not a recognized option
      {
        System.err.println("Option not recognized: " + args[i]);
        showHelp();               // show help summary
        System.exit(-1);          // exit application after printing help
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

    /* The text string in the progress bar is hard to read, because default
    colors are too soft.  UIManager can change this in the standard Sun Java
    look-and-feel, before graphical objects are created. */

//  UIManager.put("ProgressBar.selectionBackground",
//    UIManager.getColor("textText")); // remaining part of progress string
//  UIManager.put("ProgressBar.selectionForeground",
//    UIManager.getColor("textText")); // completed part of progress string

    /* Initialize shared graphical objects. */

    action = new EraseDisk1User(); // create our shared action listener
    emptyBorder = BorderFactory.createEmptyBorder(); // for removing borders
    fileChooser = new JFileChooser(); // create our shared file chooser
    statusTimer = new javax.swing.Timer(TIMER_DELAY, action);
                                  // update status message on clock ticks only

    /* Create the graphical interface as a series of little panels inside
    bigger panels.  The intermediate panel names are of no lasting importance
    and hence are only numbered (panel01, panel02, etc). */

    /* First screen with comments and a folder selection button.  The message
    text area expands and contracts with the window size. */

    drivePanel = new JPanel(new BorderLayout(10, 10));

    JTextArea panel11 = new JTextArea(10, 40); // fixed message text
    panel11.setEditable(false);   // user can't change this text area
    if (buttonFont != null) panel11.setFont(buttonFont);
    panel11.setLineWrap(true);    // allow text lines to wrap
    panel11.setOpaque(false);     // transparent background, not white
    panel11.setWrapStyleWord(true); // wrap at word boundaries
    panel11.setText("Erase the empty space on a disk drive.  "
      + COPYRIGHT_NOTICE + "\n\nBegin by selecting a folder on the correct"
      + " disk drive where large temporary files can be created.");
    JScrollPane panel12 = new JScrollPane(panel11);
    panel12.setBorder(emptyBorder);
    drivePanel.add(panel12, BorderLayout.CENTER);

    JPanel panel13 = new JPanel();
    panel13.setLayout(new BoxLayout(panel13, BoxLayout.Y_AXIS));

    JPanel panel14 = new JPanel(new BorderLayout(10, 0));
    driveFolderButton = new JButton("Drive Folder...");
    driveFolderButton.addActionListener(action);
    if (buttonFont != null) driveFolderButton.setFont(buttonFont);
    driveFolderButton.setMnemonic(KeyEvent.VK_D);
    driveFolderButton.setToolTipText("Select folder on disk drive.");
    panel14.add(driveFolderButton, BorderLayout.WEST);
    driveFolderPath = new JTextField();
    driveFolderPath.setBorder(emptyBorder);
    driveFolderPath.setEditable(false); // user can't change this text area
    if (buttonFont != null) driveFolderPath.setFont(buttonFont);
    driveFolderPath.setOpaque(false);
    panel14.add(driveFolderPath, BorderLayout.CENTER);
    panel13.add(panel14);
    panel13.add(Box.createVerticalStrut(5));

    driveFolderStatus = new JTextField();
    driveFolderStatus.setBorder(emptyBorder);
    driveFolderStatus.setEditable(false); // user can't change this text area
    if (buttonFont != null) driveFolderStatus.setFont(buttonFont);
    driveFolderStatus.setOpaque(false);
    panel13.add(driveFolderStatus);
    panel13.add(Box.createVerticalStrut(20));

    JPanel panel15 = new JPanel(new BorderLayout(20, 0));
    driveLicenseButton = new JButton("Show License...");
    driveLicenseButton.addActionListener(action);
    driveLicenseButton.setEnabled((new File(LICENSE_FILE)).isFile());
    if (buttonFont != null) driveLicenseButton.setFont(buttonFont);
    driveLicenseButton.setMnemonic(KeyEvent.VK_L);
    driveLicenseButton.setToolTipText("Show GNU General Public License (GPL).");
    panel15.add(driveLicenseButton, BorderLayout.WEST);
    driveNextButton = new JButton("Next >");
    driveNextButton.addActionListener(action);
    driveNextButton.setEnabled(false); // disable until drive folder selected
    if (buttonFont != null) driveNextButton.setFont(buttonFont);
    driveNextButton.setMnemonic(KeyEvent.VK_N);
    driveNextButton.setToolTipText("Forward to option page.");
    panel15.add(driveNextButton, BorderLayout.EAST);
    panel13.add(panel15);

    drivePanel.add(panel13, BorderLayout.SOUTH);

    /* Second screen with simple checkboxes for options. */

    optionPanel = new JPanel(new BorderLayout(10, 10));

    JTextArea panel21 = new JTextArea(5, 40); // fixed message text
    panel21.setEditable(false);   // user can't change this text area
    if (buttonFont != null) panel21.setFont(buttonFont);
    panel21.setLineWrap(true);    // allow text lines to wrap
    panel21.setOpaque(false);     // transparent background, not white
    panel21.setWrapStyleWord(true); // wrap at word boundaries
    panel21.setText("A full erase writes three times: once with all ones"
      + " (0xFF bytes), once with a pseudo-random sequence, and once with all"
      + " zeros (0x00 bytes).  A quick erase writes zeros.  Select the read"
      + " verify option only if the disk is removable or is bigger than the"
      + " physical size of your computer's memory (RAM).");
    JScrollPane panel22 = new JScrollPane(panel21);
    panel22.setBorder(emptyBorder);
    optionPanel.add(panel22, BorderLayout.CENTER);

    JPanel panel23 = new JPanel();
    panel23.setLayout(new BoxLayout(panel23, BoxLayout.Y_AXIS));

    JPanel panel24 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    optionWriteCustom = new JCheckBox("customized data pattern", false);
    optionWriteCustom.addActionListener(action);
    if (buttonFont != null) optionWriteCustom.setFont(buttonFont);
    optionWriteCustom.setMnemonic(KeyEvent.VK_C);
    panel24.add(optionWriteCustom); // this hook for later special requests
//  panel23.add(panel24);         // must match code in startErase() method

    JPanel panel25 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    optionWriteOnes = new JCheckBox("write all ones (0xFF bytes)", false);
    optionWriteOnes.addActionListener(action);
    if (buttonFont != null) optionWriteOnes.setFont(buttonFont);
    optionWriteOnes.setMnemonic(KeyEvent.VK_O);
    panel25.add(optionWriteOnes);
    panel23.add(panel25);

    JPanel panel26 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    optionWriteRandom = new JCheckBox("pseudo-random data and", true);
    optionWriteRandom.addActionListener(action);
    if (buttonFont != null) optionWriteRandom.setFont(buttonFont);
    optionWriteRandom.setMnemonic(KeyEvent.VK_R);
    panel26.add(optionWriteRandom);
    optionRandomVerify = new JCheckBox("read verify after", false);
    optionRandomVerify.addActionListener(action);
    optionRandomVerify.setEnabled(true);
    if (buttonFont != null) optionRandomVerify.setFont(buttonFont);
    optionRandomVerify.setMnemonic(KeyEvent.VK_V);
    panel26.add(optionRandomVerify);
    optionVerifyPrompt = new JCheckBox("prompt", false);
    optionVerifyPrompt.setEnabled(false);
    if (buttonFont != null) optionVerifyPrompt.setFont(buttonFont);
    optionVerifyPrompt.setMnemonic(KeyEvent.VK_P);
    panel26.add(optionVerifyPrompt);
    panel23.add(panel26);

    JPanel panel27 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    optionWriteZeros = new JCheckBox("write all zeros (0x00 bytes)", false);
    optionWriteZeros.addActionListener(action);
    if (buttonFont != null) optionWriteZeros.setFont(buttonFont);
    optionWriteZeros.setMnemonic(KeyEvent.VK_Z);
    panel27.add(optionWriteZeros);
    panel23.add(panel27);
    panel23.add(Box.createVerticalStrut(20));

    JPanel panel28 = new JPanel(new BorderLayout(20, 0));
    optionBackButton = new JButton("< Back");
    optionBackButton.addActionListener(action);
    if (buttonFont != null) optionBackButton.setFont(buttonFont);
    optionBackButton.setMnemonic(KeyEvent.VK_B);
    optionBackButton.setToolTipText("Back to drive selection.");
    panel28.add(optionBackButton, BorderLayout.WEST);
    optionNextButton = new JButton("Next >");
    optionNextButton.addActionListener(action);
    if (buttonFont != null) optionNextButton.setFont(buttonFont);
    optionNextButton.setMnemonic(KeyEvent.VK_N);
    optionNextButton.setToolTipText("Forward to erase disk.");
    panel28.add(optionNextButton, BorderLayout.EAST);
    panel23.add(panel28);

    optionPanel.add(panel23, BorderLayout.SOUTH);

    /* Third screen with erase start/stop buttons and scrolling status. */

    erasePanel = new JPanel(new BorderLayout(10, 10));

    eraseOutputText = new JTextArea(5, 40); // scrolling message text
    eraseOutputText.setEditable(false); // user can't change this text area
    if (buttonFont != null) eraseOutputText.setFont(buttonFont);
    eraseOutputText.setLineWrap(true); // allow text lines to wrap
    eraseOutputText.setOpaque(false); // transparent background, not white
    eraseOutputText.setText(ERASE_TEXT);
    eraseOutputText.setWrapStyleWord(true); // wrap at word boundaries
    JScrollPane panel31 = new JScrollPane(eraseOutputText);
    panel31.setBorder(emptyBorder);
    erasePanel.add(panel31, BorderLayout.CENTER);

    JPanel panel32 = new JPanel(); // everything else after message text
    panel32.setLayout(new BoxLayout(panel32, BoxLayout.Y_AXIS));

    eraseProgressBar = new JProgressBar(0, 100);
    eraseProgressBar.setBorderPainted(false);
    if (buttonFont != null) eraseProgressBar.setFont(buttonFont);
    eraseProgressBar.setStringPainted(true);
    panel32.add(eraseProgressBar);
    panel32.add(Box.createVerticalStrut(5));

    eraseTimeText = new JTextField(); // elapsed time and current speed
    eraseTimeText.setBorder(emptyBorder);
    eraseTimeText.setEditable(false); // user can't change this text area
    if (buttonFont != null) eraseTimeText.setFont(buttonFont);
    eraseTimeText.setHorizontalAlignment(JTextField.CENTER);
    eraseTimeText.setOpaque(false);
    panel32.add(eraseTimeText);
    panel32.add(Box.createVerticalStrut(20));

    JPanel panel33 = new JPanel(new BorderLayout(20, 0));
    eraseBackButton = new JButton("< Back");
    eraseBackButton.addActionListener(action);
    if (buttonFont != null) eraseBackButton.setFont(buttonFont);
    eraseBackButton.setMnemonic(KeyEvent.VK_B);
    eraseBackButton.setToolTipText("Back to option page.");
    panel33.add(eraseBackButton, BorderLayout.WEST);
    JPanel panel34 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    eraseStartButton = new JButton("Start");
    eraseStartButton.addActionListener(action);
    if (buttonFont != null) eraseStartButton.setFont(buttonFont);
    eraseStartButton.setMnemonic(KeyEvent.VK_S);
    eraseStartButton.setToolTipText("Start erasing disk drive.");
    panel34.add(eraseStartButton);
    panel33.add(panel34, BorderLayout.CENTER);
    eraseExitButton = new JButton("Exit");
    eraseExitButton.addActionListener(action);
    if (buttonFont != null) eraseExitButton.setFont(buttonFont);
    eraseExitButton.setMnemonic(KeyEvent.VK_X);
    eraseExitButton.setToolTipText("Close this program.");
    panel33.add(eraseExitButton, BorderLayout.EAST);
    panel32.add(panel33);

    erasePanel.add(panel32, BorderLayout.SOUTH);

    /* Create the main window frame for this application.  We use a border
    layout to add margins around a central area for the drive/option/erase
    panels. */

    mainFrame = new JFrame(PROGRAM_TITLE);
    mainPanel = mainFrame.getContentPane(); // where content meets frame
    mainPanel.setLayout(new BorderLayout(0, 0));
    mainPanel.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
    mainPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    mainPanel.add(drivePanel, BorderLayout.CENTER); // initial panel
    mainPanel.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
    mainPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLocation(windowLeft, windowTop); // normal top-left corner
    if ((windowHeight < MIN_FRAME) || (windowWidth < MIN_FRAME))
      mainFrame.pack();           // do component layout with minimum size
    else                          // the user has given us a window size
      mainFrame.setSize(windowWidth, windowHeight); // size of normal window
    if (maximizeFlag) mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainFrame.validate();         // recheck application window layout
    mainFrame.setVisible(true);   // and then show application window

    /* Let the graphical interface run the application now. */

    driveFolderButton.requestFocusInWindow(); // give keyboard focus to button

  } // end of main() method

// ------------------------------------------------------------------------- //

/*
  appendEraseText() method

  Append a line or lines of text to the erase message text.  We add a newline
  character at the end of the line, not the caller.

  The output text area is forced to scroll to the end, after the text line is
  written, by selecting character positions that are much too large (and which
  are allowed by the definition of the JTextComponent.select() method).  This
  is easier and faster than manipulating the scroll bars directly.  However, it
  does cancel any selection that the user might have made, for example, to copy
  text from the output area.
*/
  static void appendEraseText(String text)
  {
    eraseOutputText.append(text); // append the caller's text
    eraseOutputText.append("\n"); // append a trailing newline
    eraseOutputText.select(999999999, 999999999); // force scroll to end
  }


/*
  checkDriveFolder() method

  If a writeable folder has been selected on the drive page, then enable the
  "Next" button.  This method is overly protective because it is possible to
  select a good folder, continue to the erase page, delete that folder, then
  return to the drive page.
*/
  static void checkDriveFolder()
  {
    if (driveSelection == null)   // not likely to happen, but be complete
    {
      driveFolderPath.setText(null); // remove text from folder path name
      driveFolderStatus.setText(null); // clear status message
      driveNextButton.setEnabled(false); // don't continue from this page
      return;                     // and don't do anything more
    }
    driveFolderPath.setText(driveSelection.getPath()); // selected path name
    if (driveSelection.exists() == false) // if somehow selected bad folder
    {
      driveFolderStatus.setText("Sorry, that file or folder doesn't exist.");
      driveNextButton.setEnabled(false); // don't continue from this page
      driveSelection = null;      // disable the selected folder (don't use)
    }
    else if (driveSelection.isDirectory() == false) // somehow selected file
    {
      driveFolderStatus.setText("Sorry, that is not a directory or folder.");
      driveNextButton.setEnabled(false); // don't continue from this page
      driveSelection = null;      // disable the selected folder (don't use)
    }
    else if (driveSelection.canWrite() == false) // not a writeable folder
    {
      driveFolderStatus.setText("Sorry, can't write to that directory or folder.");
      driveNextButton.setEnabled(false); // don't continue from this page
      driveSelection = null;      // disable the selected folder (don't use)
    }
    else                          // we are "good to go" (Mission Control)
    {
      driveFolderStatus.setText(null); // clear status message
      driveNextButton.setEnabled(true); // may now continue from this page
    }
  } // end of checkDriveFolder() method


/*
  checkOptionNext() method

  The "Next" button on the option page is only enabled if at least one checkbox
  has been selected for a writing pattern.
*/
  static void checkOptionNext()
  {
    optionNextButton.setEnabled(optionWriteCustom.isSelected()
      || optionWriteOnes.isSelected() || optionWriteRandom.isSelected()
      || optionWriteZeros.isSelected());
  }


/*
  createFilename() method

  Common routine to create a temporary file name given a file number, so that
  all methods create the same file names in the old MS-DOS 8.3 format.
*/
  static String createFilename(int number)
  {
    String digits = String.valueOf(number); // convert integer to characters
    return("ERASE000".substring(0, (8 - digits.length())) + digits + ".DAT");
  }


/*
  formatBufferSize() method

  Given a buffer size in bytes, format a string similar to what we accept on
  the command line for buffer sizes and file sizes.  Buffer sizes are assumed
  to be nice powers of two, unlike file sizes.  Negative numbers and zero are
  not valid parameters.
*/
  static String formatBufferSize(long size)
  {
    long units = size;            // start with bytes, reduce to KB, MB, etc
    String suffix = "";           // no suffix for bytes
    if (units > 0)                // only positive numbers make sense here
    {
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "K"; }
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "M"; }
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "G"; }
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "T"; }
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "P"; }
      if ((units & 0x3FF) == 0) { units = units >> 10; suffix = "E"; }
    }
    return(units + suffix);       // scaled into units
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
  static long parseFileSize(String text)
  {
    Matcher matcher;              // pattern matcher for given string
    Pattern pattern;              // compiled regular expression
    char prefix;                  // first character of suffix (if any)
    long result;                  // our result (the file size)
    String suffix;                // suffix string (may be null)

    result = -1;                  // assume file size is invalid
    pattern = Pattern.compile(    // compile our regular expression
      "(\\d+)(|b|k|kb|kib|m|mb|mib|g|gb|gib|t|tb|tib|p|pb|pib|e|eb|eib)");
    matcher = pattern.matcher(text.toLowerCase()); // parse given string
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
  randomNextByte() method

  Return the next byte in a pseudo-random number sequence.  This is a greatly
  reduced version of the standard java.util.Random methods.  The result should
  be the same as the low-order 8 bits of Random.nextInt() given the same seed.
*/
  static byte randomNextByte()
  {
    randomSeed = (randomSeed * RANDOM_MULTIPLIER + RANDOM_ADDEND)
      & RANDOM_MASK;              // standard linear congruential generator
    return((byte) ((randomSeed >>> 16) & BYTE_MASK)); // select bits for byte
  }


/*
  randomSetSeed() method

  Set the seed for the pseudo-random number generator.  Again, this code mimics
  the standard java.util.Random methods.  The global variable <randomSeed> does
  not have a default value in this program; randomSetSeed() must be called.
*/
  static void randomSetSeed(long seed)
  {
    randomSeed = (seed ^ RANDOM_MULTIPLIER) & RANDOM_MASK;
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
    System.err.println("This is a graphical application.  You may give options on the command line:");
    System.err.println();
    System.err.println("  -? = -help = show summary of command-line syntax");
    System.err.println("  -b# = data buffer size in bytes from "
      + formatBufferSize(BUFFER_SIZE_LOWER) + "B to "
      + formatBufferSize(BUFFER_SIZE_UPPER) + "B; default: -b"
      + formatBufferSize(BUFFER_SIZE_DEFAULT[0]) + "B");
    System.err.println("  -d = show debug information (may be verbose)");
    System.err.println("  -f# = maximum number of temporary files from "
      + MAX_FILE_LOWER + " to " + MAX_FILE_UPPER + "; default: -f"
      + MAX_FILE_DEFAULT);
    System.err.println("  -s# = maximum bytes per temporary file, rounded to next full data buffer;");
    System.err.println("      default: -s" + formatBufferSize(MAX_SIZE_DEFAULT) + "B");
    System.err.println("  -u# = font size for buttons, dialogs, etc; default is local system;");
    System.err.println("      example: -u16");
    System.err.println("  -w(#,#,#,#) = normal window position: left, top, width, height;");
    System.err.println("      example: -w(50,50,700,500)");
    System.err.println("  -x = maximize application window; default is normal window");
    System.err.println();
    System.err.println(COPYRIGHT_NOTICE);
//  System.err.println();

  } // end of showHelp() method


/*
  startErase() method

  Erase the disk drive.  This method should be called from a secondary thread,
  not from the main thread that runs the GUI.
*/
  static void startErase()
  {
    String oldButtonText;         // previous text for "Start" button
    int oldMnemonic;              // previous keyboard shortcut for "Start"
    String oldTooltip;            // previous mouse caption for "Start"
    long timeUsed;                // total elapsed time in milliseconds

    /* Initialize local and global variables. */

    cancelFlag = false;           // don't cancel unless user complains
    deleteFlag = true;            // we should delete our temporary files
    eraseFlag = true;             // show that we are actively erasing disk
    startTime = System.currentTimeMillis(); // starting time in milliseconds
    userBytesDone = userBytesPrev = 0; // no bytes reported to user yet
    userBytesRate = -1.0;         // no current or previous bytes per second
    userBytesTotal = 0;           // unknown total for Java 1.4 or 5.0
//  userBytesTotal = driveSelection.getFreeSpace(); // Java 6 or later

    /* Replace the "Start" button with a "Cancel" button. */

    oldButtonText = eraseStartButton.getText(); // save previous values
    oldMnemonic = eraseStartButton.getMnemonic();
    oldTooltip = eraseStartButton.getToolTipText();
    eraseOutputText.setText(ERASE_TEXT); // reset our scrolling text area
    eraseProgressBar.setString(""); // empty string, not built-in percent
    eraseProgressBar.setValue(0); // and clear any previous status value
    eraseStartButton.setText("Cancel");
    eraseStartButton.setMnemonic(KeyEvent.VK_C);
    eraseStartButton.setToolTipText("Stop erasing disk drive.");
    eraseTimeText.setText(null);  // clear elapsed time and current speed
    appendEraseText("Erasing in drive folder " + driveSelection.getPath());
    if (userBytesTotal > 0)       // only if we have a reasonable estimate
      appendEraseText("Estimated free space is "
        + formatComma.format(userBytesTotal) + " bytes.");
    statusTimer.start();          // start updating the status message

    /* Erase the disk one or more times, with an optional verify. */

    if ((cancelFlag == false) && optionWriteCustom.isSelected())
    {
      appendEraseText("\nWriting customized data pattern...");
      appendEraseText("Sorry, no custom pattern has been defined.");
    }
    if ((cancelFlag == false) && optionWriteOnes.isSelected())
    {
      appendEraseText("\nWriting all ones (0xFF)...");
      startEraser(false, 0xFF, false, false); // no verify
    }
    if ((cancelFlag == false) && optionWriteRandom.isSelected())
    {
      appendEraseText("\nWriting pseudo-random data...");
      startEraser(true, 0x83, optionRandomVerify.isSelected(),
        optionVerifyPrompt.isSelected());
    }
    if ((cancelFlag == false) && optionWriteZeros.isSelected())
    {
      appendEraseText("\nWriting all zeros (0x00)...");
      startEraser(false, 0x00, false, false); // no verify
    }

    /* Tell the user that we are done.  With long delays while writing to the
    disk, it's not so obvious when we are truly finished. */

    if (cancelFlag == false)      // if the user didn't interrupt us
    {
      timeUsed = System.currentTimeMillis() - startTime; // milliseconds
      appendEraseText("\nDone in " + formatHours(timeUsed) + " ("
        + formatClock(timeUsed) + ")."); // elapsed time and scaled units
    }

    /* Change the "Cancel" button back into a "Start" button. */

    statusTimer.stop();           // stop updating status message by timer
    eraseFlag = false;            // no longer actively erasing disk
    eraseStartButton.setText(oldButtonText); // restore previous values
    eraseStartButton.setMnemonic(oldMnemonic);
    eraseStartButton.setToolTipText(oldTooltip);
    eraseStartButton.setEnabled(true); // may be disabled if "Cancel" used
    eraseTimeText.setText(null);  // clear elapsed time and current speed

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
    boolean randomFlag,           // true if using pseudo-random sequence
    int fillByte,                 // 0x00 or 0xFF data, ignored if random
    boolean verifyFlag,           // true if we read verify after writing
    boolean promptFlag)           // true if we prompt user before verify
  {
    byte[] buffer;                // a byte buffer for reading, writing files
    byte compareByte;             // one comparison value during read verify
    long fileBytes;               // number of bytes written to current file
    File fp;                      // File object to write, read, or delete
    int i;                        // index variable
    FileInputStream inp;          // byte input stream for reading one file
    int numFiles;                 // number of files that we have created
    FileOutputStream out;         // byte output stream for writing one file
    long passBegin, passUsed;     // starting, total times for read/write pass
    long ranBase;                 // start all random sequences from same base
    long recentCorrect, recentErrors; // recent good, bad verify bytes one file
    boolean recentFail;           // true if comparison has failed for one file
    int sizeEntry;                // index of current entry in <bufferSizes>
    int sizeLimit;                // first and maximum size from <bufferSizes>
    int startIndex;               // beginning of current piece in write buffer
    int thisFile;                 // current file number, up to <numFiles>
    int thisSize;                 // current buffer size that we are using
    long totalCorrect, totalErrors; // total good, bad verify bytes all files

    /* Begin by ending early if the user has cancelled. */

    if (cancelFlag) return;       // stop if user hit the panic button

    /* Initialize local and global variables. */

    passBegin = System.currentTimeMillis(); // starting time for write pass
    ranBase = System.currentTimeMillis(); // base for random number sequences
    userBytesDone = userBytesPrev = 0; // no bytes reported to user yet
    userBytesRate = -1.0;         // no current or previous bytes per second

    /* Create and fill a buffer for writing files.  Pseudo-random sequences are
    adjusted later. */

    sizeLimit = bufferSizes[0];   // first size assumed to be largest in list
    buffer = new byte[sizeLimit]; // allocate buffer at maximum/preferred size
    for (i = 0; i < sizeLimit; i ++)
      buffer[i] = (byte) fillByte; // use same value for entire buffer

    /* Keep creating new files until the disk drive is full, or we reach our
    limit on the number of files.  Some file systems such as NTFS store very
    small files inside the file structure (around 728 bytes or less for NTFS).
    While we could create zillions of these little files, erasing them is best
    done by reformatting the disk drive.  We stop after the total size of a
    temporary file is smaller than our maximum/preferred buffer size. */

    numFiles = 0;                 // no files created yet
    while ((cancelFlag == false) && (numFiles < maxFileCount))
                                  // outer <while> loop
    {
      /* Try to create a new temporary file. */

      numFiles ++;                // one more temporary file will be created
      fp = new File(driveSelection, createFilename(numFiles)); // from name
      try { out = new FileOutputStream(fp); } // we do our own buffering
      catch (FileNotFoundException fnfe) // the only documented exception
      {
        numFiles --;              // we failed, so don't count this file
        appendEraseText(fp.getName() + " - can't create temporary file");
        break;                    // exit early from outer <while> loop
      }
      if (debugFlag)              // does user want details?
        appendEraseText(fp.getName() + " - temporary file created");

      /* Keep writing to the file until it's full.  The first few errors will
      be because our buffer is too big, and writing smaller pieces will work.
      Even when switching to smaller pieces, we try to finish the full buffer
      in sequence, to preserve random data that has already been generated. */

      fileBytes = 0;              // no bytes written to this file yet
      randomSetSeed(ranBase + numFiles); // start each file with a known value
                                  // ... that varies slightly per file
      sizeEntry = 0;              // index of first entry in buffer size list
      startIndex = sizeLimit;     // flag buffer as needing refill
      thisSize = bufferSizes[sizeEntry]; // value of first entry in size list
      if (debugFlag)              // does user want details?
        appendEraseText(fp.getName() + " - data buffer size is "
          + formatBufferSize(thisSize) + " bytes");
      while ((cancelFlag == false) && (fileBytes < maxFileSize))
                                  // inner <while> loop
      {
        if (startIndex >= sizeLimit) // need to refill the data buffer?
        {
          /* It is very tempting to use Random.nextBytes(buffer) here, but the
          read verify can't assume full buffers and needs to repeat the random
          sequence one byte (number) at a time.  The nextBytes() method will
          generate different sequences if called 256 times for one byte versus
          one call for 256 bytes. */

          startIndex = 0;         // reset to beginning of full buffer
          if (randomFlag)         // buffer data only changes if randomized
            for (i = 0; i < sizeLimit; i ++)
              buffer[i] = randomNextByte(); // use our customized generator
        }
        try                       // can we write some more to the file?
        {
          out.write(buffer, startIndex, thisSize); // try to write selection
          if (thisSize < sizeLimit) // once we start using smaller sizes
            out.flush();          // always force a disk flush on each write
        }
        catch (IOException ioe)   // assume all errors are "disk may be full"
        {
          if (debugFlag)          // does user want details?
            appendEraseText(fp.getName() + " - " + ioe.getMessage());
          sizeEntry ++;           // index of next entry in buffer size list
          if (sizeEntry >= bufferSizes.length) // stop if no more sizes
            break;                // exit early from inner <while> loop
          thisSize = bufferSizes[sizeEntry]; // value of next entry in list
          if (debugFlag)          // does user want details?
            appendEraseText(fp.getName() + " - buffer size reduced to "
              + formatBufferSize(thisSize) + " bytes");
          continue;               // back to beginning of inner <while> loop
        }
        if (debugFlag && (thisSize < sizeLimit)) // does user want details?
          appendEraseText(fp.getName() + " - successfully wrote "
            + formatBufferSize(thisSize) + " bytes");
        fileBytes += thisSize;    // we successfully appended to this file
        startIndex += thisSize;   // advance beginning of next buffer piece
        userBytesDone += thisSize; // and show this on the progress bar
      }
      try { out.close(); } catch (IOException ioe) { /* ignore errors */ }
      if (cancelFlag == false)    // if the user didn't interrupt us
        appendEraseText(fp.getName() + " - " + formatComma.format(fileBytes)
          + " bytes written");
      if (fileBytes < sizeLimit)  // disk is full if less than one buffer
        break;                    // exit early from outer <while> loop
    }

    /* Summary after writing all files. */

    if (cancelFlag == false)      // if the user didn't interrupt us
    {
      userBytesTotal = userBytesDone; // save for verify status or next pass
      updateProgressBar();        // force the progress bar to update
      if (numFiles >= maxFileCount) // normally, we never reach the maximum
      {
        appendEraseText(
          "Temporary file limit reached; erase may not be complete.");
      }
      appendEraseText("Created " + prettyPlural(numFiles, "temporary file")
        + " with " + formatComma.format(userBytesDone) + " bytes.");

      passUsed = System.currentTimeMillis() - passBegin;
                                  // elapsed time for this write pass
      if (passUsed > 0)           // don't want to divide by zero
        appendEraseText("Average write speed was " + formatSpeed((double)
          userBytesDone * 1000.0 / (double) passUsed)
          + " over " + formatHours(passUsed) // too much information?
          + ".");                 // nicely end the sentence
    }

    /* Read verify the written data, if this option was chosen by the user.
    Most of the time is spent on tedious byte-by-byte accounting for correct
    and error bytes, and for re-generating any random sequence.  Something to
    note about Java is that while we may ask for <sizeLimit> bytes on each
    read, the run-time may return fewer bytes even if there is still more data
    in the file.  Don't assume that less than a full buffer means end-of-file.

    Java 1.4 has no standard way of invalidating disk caches in hardware or the
    underlying operating system.  If the amount of data written is smaller than
    the physical memory (RAM) on the computer, data that we read may be fetched
    from the cache and not from the disk.  For removable media, one possible
    solution is a pop-up dialog that asks the user to remove (eject) and then
    reinsert the media. */

    if ((cancelFlag == false) && verifyFlag)
    {
      /* Prompt the user to eject and reinsert any removable media such as a
      floppy disk or USB flash drive. */

      appendEraseText("Reading file data to verify...");
      if (promptFlag)             // don't really know if media is removable
        JOptionPane.showMessageDialog(mainFrame,
          ("If your disk is on removable media, then:\n"
          + "1. Remove (eject) the disk normally;\n"
          + "2. Reinsert the disk; and\n"
          + "3. Click the OK button here."));

      /* Keep reading from the file until we reach an end-of-file.  We don't
      match file sizes for each file that we wrote above, only the totals. */

      compareByte = (byte) fillByte; // use same value except for random data
      passBegin = System.currentTimeMillis(); // starting time for read pass
      thisFile = 1;               // start with the first file we created
      totalCorrect = totalErrors = 0; // global number of good and bad bytes
      userBytesDone = userBytesPrev = 0; // no bytes reported to user yet
      userBytesRate = -1.0;       // no current or previous bytes per second
      while ((cancelFlag == false) && (thisFile <= numFiles))
      {
        /* Try to open a previously created temporary file. */

        fp = new File(driveSelection, createFilename(thisFile)); // from name
        try { inp = new FileInputStream(fp); } // we do our own buffering
        catch (FileNotFoundException fnfe) // the only documented exception
        {
          appendEraseText(fp.getName() + " - can't read temporary file");
          break;                  // exit early from outer <while> loop
        }
        if (debugFlag)            // does user want details?
          appendEraseText(fp.getName() + " - reading temporary file");

        /* Keep reading from the file until an end-of-file or I/O error.  There
        is no special meaning if the number of bytes read is positive but less
        than the size of our buffer, so don't assume end-of-file. */

        fileBytes = 0;            // no bytes read from this file yet
        randomSetSeed(ranBase + thisFile); // start each file with a known
                                  // ... value that varies slightly per file
        recentCorrect = recentErrors = 0; // local number of good and bad bytes
        recentFail = false;       // true if comparison has at least one error
        while ((cancelFlag == false) && (fileBytes >= 0))
        {
          try { thisSize = inp.read(buffer); } // try to read from file
          catch (IOException ioe) // all errors are bad news when reading
          {
            appendEraseText(fp.getName() + " - " + ioe.getMessage());
            fileBytes = -1;       // mark number of bytes read as invalid
            break;                // exit early from inner <while> loop
          }
          if (thisSize <= 0)      // stop if we reached the end-of-file
            break;                // exit early from inner <while> loop

          /* Compare the data read with the correct data. */

          for (i = 0; i < thisSize; i ++) // for all input bytes
          {
            if (randomFlag)       // are we reading a pseudo-random sequence?
              compareByte = randomNextByte(); // use our customized generator
            if (buffer[i] == compareByte) // compare data read with expected
            {
              recentCorrect ++;   // one more consecutively correct byte
              totalCorrect ++;    // increase total number of correct bytes
              if (recentCorrect >= ERROR_RESET) // enough to forgive an error?
              {
                recentCorrect = 0; // clear local counter for number correct
                if (recentErrors > 0) // are there any recent errors?
                  recentErrors --; // yes, reduce local error count by one
              }
            }
            else                  // what we read is not what we wanted
            {
              appendEraseText(fp.getName() + " - byte at 0x"
                + Long.toHexString(fileBytes + i).toUpperCase() + " is 0x"
                + Integer.toHexString(buffer[i] & BYTE_MASK).toUpperCase()
                + " but should be 0x"
                + Integer.toHexString(compareByte & BYTE_MASK).toUpperCase());
              recentCorrect = 0;  // clear counter for local number correct
              recentErrors ++;    // increase local error count by one
              recentFail = true;  // comparison has failed for this file
              totalErrors ++;     // increase total number of errors detected
              if (recentErrors >= ERROR_LIMIT) // too many errors too quickly?
              {
                appendEraseText(fp.getName()
                  + " - too many errors, stopping after "
                  + prettyPlural((fileBytes + i + 1), "byte"));
                fileBytes = -1;   // mark number of bytes read as invalid
                break;            // exit early from <for> loop
              }
            }
          }
          if (fileBytes < 0)      // did something go wrong with comparison?
            break;                // exit early from inner <while> loop
          fileBytes += thisSize;  // comparison was acceptable, add to totals
          userBytesDone += thisSize; // and show this on the progress bar too
        }
        try { inp.close(); } catch (IOException ioe) { /* ignore errors */ }
        if ((cancelFlag == false) && (fileBytes >= 0) && (recentFail == false))
          appendEraseText(fp.getName() + " - " + formatComma.format(fileBytes)
            + " bytes correct");
        thisFile ++;              // now do the next temporary file
      }

      /* Summary after reading all files. */

      if (cancelFlag == false)    // if the user didn't interrupt us
      {
        updateProgressBar();      // force the progress bar to update
        if ((totalCorrect == userBytesTotal) && (totalErrors == 0))
        {
          appendEraseText("Verified "
            + prettyPlural(numFiles, "temporary file") + " with "
            + formatComma.format(userBytesTotal) + " bytes.");

          passUsed = System.currentTimeMillis() - passBegin;
                                  // elapsed time for this read pass
          if (passUsed > 0)       // don't want to divide by zero
            appendEraseText("Average read speed was " + formatSpeed((double)
              userBytesDone * 1000.0 / (double) passUsed)
              + " over " + formatHours(passUsed) // too much information?
              + ".");             // nicely end the sentence
        }
        else                      // we didn't read what we previously wrote
        {
          appendEraseText("Verify failed with "
            + formatComma.format(userBytesTotal) + " bytes written but only "
            + formatComma.format(totalCorrect) + " bytes correct.");
        }
      }
    }

    /* Delete our temporary files.  We ignore most errors here. */

    if (deleteFlag)               // should we delete our temporary files?
    {
      thisFile = 0;               // number of files successfully deleted
      for (i = 1; i <= numFiles; i ++) // for each file that we created
      {
        fp = new File(driveSelection, createFilename(i)); // from file name
        if (fp.delete())          // try to delete this file
        {
          thisFile ++;            // one more file deleted
          if (debugFlag)          // does user want details?
            appendEraseText(fp.getName() + " - temporary file deleted");
        }
        else                      // something went wrong
          appendEraseText(fp.getName() + " - failed to delete file");
      }
      appendEraseText("Deleted " + prettyPlural(thisFile, "temporary file")
        + ".");
    }
  } // end of startEraser() method


/*
  updateProgressBar() method

  Update the progress bar (status timer) at scheduled clock ticks, so that the
  text doesn't change too quickly, and the progress bar doesn't advance too
  smoothly.  Since this method runs in a separate timer thread (independent),
  we are careful to work with consistent local copies of global variables, and
  to set text fields as single, complete strings.
*/
  static void updateProgressBar()
  {
    StringBuffer buffer;          // for creating complete text strings
    long done = userBytesDone;    // get local copy so it doesn't change
    double rate;                  // current (most recent) bytes per second
    long total = userBytesTotal;  // get local copy so it doesn't change

    /* Update progress bar with bytes done, total, and percent. */

    buffer = new StringBuffer();  // faster than String for appends
    buffer.append(formatComma.format(done)); // always want number done
    if (total > 0)                // much nicer once we know total bytes
    {
      buffer.append(" bytes of ");
      buffer.append(formatComma.format(total));
      if (done < total)           // can we calculate percent done?
      {
        double percent = (100.0 * (double) done) / (double) total;
        eraseProgressBar.setValue((int) percent); // set size of progress bar
        buffer.append(" or ");
        buffer.append(formatPointOne.format(percent));
        buffer.append("%");
      }
      else                        // the total can vary on multiple passes
      {
        eraseProgressBar.setValue(100); // set maximum size of progress bar
        buffer.append(" total");  // don't report percent if done or over 100%
      }
    }
    else                          // can't give percent unless we know total
    {
      buffer.append(" bytes written, unknown total");
    }
    eraseProgressBar.setString(buffer.toString()); // set text for progress bar

    /* Update elapsed time and current speed.  A weighted average with a short
    delay shows smoother values for the speed. */

    rate = (double) (done - userBytesPrev) * 1000.0 / TIMER_DELAY;
    if (userBytesRate < 0.0)      // were there previous bytes per second?
      userBytesRate = rate;       // no, fix calculation with current rate
    eraseTimeText.setText(formatClock(System.currentTimeMillis() - startTime)
      + " elapsed at " + formatSpeed((rate * 0.7) + (userBytesRate * 0.3)));
                                  // scale into nice units per second
    userBytesPrev = done;         // remember previously reported amount
    userBytesRate = rate;         // remember current bytes per second

  } // end of updateProgressBar() method


/*
  userButton() method

  This method is called by our action listener actionPerformed() to process
  buttons, in the context of the main EraseDisk1 class.
*/
  static void userButton(ActionEvent event)
  {
    Object source = event.getSource(); // where the event came from
    if (source == driveFolderButton) // "Drive Folder" button on drive page
    {
      fileChooser.resetChoosableFileFilters(); // remove any existing filters
      fileChooser.setDialogTitle("Select Writeable Drive Folder...");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.setMultiSelectionEnabled(false); // allow only one folder
      if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)
      {
        driveSelection = fileChooser.getSelectedFile(); // correct Java object
        checkDriveFolder();       // get someone else to check that folder
      }
    }
    else if (source == driveLicenseButton) // "License" button on drive page
    {
      JTextArea text = new JTextArea(16, 48); // where to put license text
      text.setEditable(false);    // user can't change this text area
      text.setFont(new Font(SYSTEM_FONT, Font.PLAIN, 18)); // preformatted
      text.setLineWrap(false);    // don't wrap text lines
      text.setOpaque(false);      // transparent background, not white
      try { text.read(new FileReader(LICENSE_FILE), null); } // load text
      catch (IOException ioe)     // includes FileNotFoundException
      {
        JOptionPane.showMessageDialog(mainFrame,
          ("Sorry, can't read from text file:\n" + LICENSE_FILE));
        return;                   // do nothing and ignore License button
      }
      JScrollPane scroll = new JScrollPane(text); // will need scroll bars
      scroll.setBorder(BorderFactory.createEmptyBorder()); // but no borders
      JOptionPane.showMessageDialog(mainFrame, scroll,
        "GNU General Public License (GPL)", JOptionPane.PLAIN_MESSAGE);
    }
    else if (source == driveNextButton) // "Next" button on drive page
    {
      /* "Next" button on drive page is enabled only if a writeable folder has
      been selected. */

      mainPanel.remove(drivePanel); // remove drive page from layout
      mainPanel.add(optionPanel, BorderLayout.CENTER); // add option page
      mainPanel.validate();       // recheck application window layout
      mainPanel.repaint();        // and redraw application window
      optionNextButton.requestFocusInWindow(); // must give keyboard focus
    }
    else if (source == eraseBackButton) // "Back" button on erase page
    {
      /* The "Back" button on the erase page also acts as a "Cancel" button.
      Since we return to the option page, there is no reason to print a message
      (on the erase page) noting the "Back" button, or to disable the "Cancel"
      button, unlike what we do for <eraseStartButton> below. */

      if ((cancelFlag == false) && eraseFlag) // actively erasing disk drive?
      {
        int reply = JOptionPane.showConfirmDialog(mainFrame,
          "Back button clicked during erase.\nDelete temporary files first?");
        if (reply == JOptionPane.CANCEL_OPTION)
          return;                 // do nothing and ignore Back button
        else                      // otherwise the answer was "Yes" or "No"
          deleteFlag = (reply == JOptionPane.YES_OPTION);
//      appendEraseText("Back button clicked during erase.");
        cancelFlag = true;        // tell other threads that all work stops now
//      eraseStartButton.setEnabled(false); // don't allow multiple cancels
      }
      mainPanel.remove(erasePanel);
      mainPanel.add(optionPanel, BorderLayout.CENTER);
      mainPanel.validate();
      mainPanel.repaint();
      optionBackButton.requestFocusInWindow();
    }
    else if (source == eraseExitButton) // "Exit" button on erase page
    {
      /* "Exit" button on erase page is immediate without asking questions. */

      System.exit(0);             // always exit with zero status from GUI
    }
    else if (source == eraseStartButton) // "Start" button on erase page
    {
      /* "Start" button on erase page alternates with "Cancel" button. */

      if (eraseFlag)              // are we actively erasing disk drive?
      {
        int reply = JOptionPane.showConfirmDialog(mainFrame,
          "Cancel button clicked during erase.\nDelete temporary files first?");
        if (reply == JOptionPane.CANCEL_OPTION)
          return;                 // do nothing and ignore Cancel button
        else                      // otherwise the answer was "Yes" or "No"
          deleteFlag = (reply == JOptionPane.YES_OPTION);
        appendEraseText("Cancel button clicked during erase.");
        cancelFlag = true;        // tell other threads that all work stops now
        eraseStartButton.setEnabled(false); // don't allow multiple cancels
      }
      else                        // not active, so start secondary thread
      {
        Thread th = new Thread(new EraseDisk1User(), "eraseThread");
        th.setPriority(Thread.MIN_PRIORITY); // use lowest priority in Java VM
        th.start();               // now run as separate thread to erase disk
      }
    }
    else if (source == optionBackButton) // "Back" button on option page
    {
      checkDriveFolder();         // enable/disable "Next" on drive page
      mainPanel.remove(optionPanel);
      mainPanel.add(drivePanel, BorderLayout.CENTER);
      mainPanel.validate();
      mainPanel.repaint();
      driveFolderButton.requestFocusInWindow();
    }
    else if (source == optionNextButton) // "Next" button on option page
    {
      eraseOutputText.setText(ERASE_TEXT); // reset our scrolling text area
      eraseProgressBar.setString(""); // empty string, not built-in percent
      eraseProgressBar.setValue(0); // and clear any previous status value
      eraseTimeText.setText(null); // clear elapsed time and current speed
      appendEraseText("Selected drive folder is " + driveSelection.getPath());
      mainPanel.remove(optionPanel);
      mainPanel.add(erasePanel, BorderLayout.CENTER);
      mainPanel.validate();
      mainPanel.repaint();
      eraseStartButton.requestFocusInWindow();
    }
    else if (source == optionRandomVerify) // checkbox for random read verify
    {
      optionVerifyPrompt.setEnabled(optionRandomVerify.isSelected());
    }
    else if (source == optionWriteCustom) // checkbox for custom pattern
    {
      checkOptionNext();          // enable/disable "Next" on option page
    }
    else if (source == optionWriteOnes) // checkbox for writing ones
    {
      checkOptionNext();          // enable/disable "Next" on option page
    }
    else if (source == optionWriteRandom) // checkbox for pseudo-random write
    {
      checkOptionNext();          // enable/disable "Next" on option page
      optionRandomVerify.setEnabled(optionWriteRandom.isSelected());
      optionVerifyPrompt.setEnabled(optionRandomVerify.isSelected()
        && optionWriteRandom.isSelected());
    }
    else if (source == optionWriteZeros) // checkbox for writing zeros
    {
      checkOptionNext();          // enable/disable "Next" on option page
    }
    else if (source == statusTimer) // update timer for status message text
    {
      updateProgressBar();        // force the progress bar to update
    }
    else                          // fault in program logic, not by user
    {
      System.err.println("Error in userButton(): unknown ActionEvent: "
        + event);                 // should never happen, so write on console
    }
  } // end of userButton() method

} // end of EraseDisk1 class

// ------------------------------------------------------------------------- //

/*
  EraseDisk1User class

  This class listens to input from the user and passes back event parameters to
  a static method in the main class.
*/

class EraseDisk1User implements ActionListener, Runnable
{
  /* empty constructor */

  public EraseDisk1User() { }

  /* button listener, dialog boxes, etc */

  public void actionPerformed(ActionEvent event)
  {
    EraseDisk1.userButton(event);
  }

  /* separate heavy-duty processing thread */

  public void run() { EraseDisk1.startErase(); }

} // end of EraseDisk1User class

/* Copyright (c) 2009 by Keith Fenske.  GNU General Public License (GPLv3+). */
