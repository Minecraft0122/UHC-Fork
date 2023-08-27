package com.gmail.val59000mc.maploader;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * {@link FileVisitor} that can be used to copy Minecraft worlds using {@link Files#walkFileTree(Path, FileVisitor)}.
 *
 * <p>
 *     The visitor takes care of Minecraft-specific details, such as certain files that need
 *     to be excluded from the copy operation in order to prevent problems when loading the world.
 * </p>
 */
public class CopyWorldFileVisitor extends SimpleFileVisitor<Path> {

	private final Path sourceDir;
	private final Path destinationDir;

	public CopyWorldFileVisitor(Path sourceDir, Path destinationDir) {
		this.sourceDir = sourceDir;
		this.destinationDir = destinationDir;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		final Path relativizedDir = sourceDir.relativize(dir);
		Files.createDirectory(destinationDir.resolve(relativizedDir));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		final Path relativizedFile = sourceDir.relativize(file);
		if (!shouldExclude(relativizedFile)) {
			Files.copy(file, destinationDir.resolve(relativizedFile), LinkOption.NOFOLLOW_LINKS);
		}
		return FileVisitResult.CONTINUE;
	}

	private boolean shouldExclude(Path relativizedFile) {
		return
			// Don't copy Spigot's uid.dat file, let a new one be generated.
			// Otherwise, we can't load the copied world if the template world
			// is already loaded (since they will have the same UID).
			relativizedFile.equals(Paths.get("uid.dat")) ||
			relativizedFile.equals(Paths.get("session.lock"));
	}

}
