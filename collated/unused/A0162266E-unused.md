# A0162266E-unused
###### \SaveCommandTest.java
``` java

// Attempted to write test for writing to a directory with insufficient permissions
// The test might work in *nix systems but fails on Windows due to it not supporting Posix
@Test
public void save_directoryNotWritable_failure() {
    File lockedFolder = new File("src\\test\\data\\sandbox\\lockedFolder");
    lockedFolder.mkdir();

    Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.OTHERS_READ);

    try {
        Files.setPosixFilePermissions(Paths.get("src\\test\\data\\sandbox\\lockedFolder"), perms);
    } catch (IOException e) {
        e.printStackTrace();
    }
    commandBox.runCommand("save src\\test\\data\\sandbox\\lockedFolder");
    assertResultMessage(SaveCommand.MESSAGE_DIRECTORY_NOT_WRITABLE);
}
```
