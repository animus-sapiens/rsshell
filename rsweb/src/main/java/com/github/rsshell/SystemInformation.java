package com.github.rsshell;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.VirtualMemory;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.NetworkIF;
import oshi.hardware.GraphicsCard;
import oshi.hardware.Display;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.List;
import java.util.Locale;

/**
 * System information fetcher.
 */
public class SystemInformation {

    private static SystemInfo systemInfo = new SystemInfo();
    private static String lineSeparator = System.getProperty("line.separator");

    /**
     * Gathers all sys-info.
     * @return
     */
    public static String getSystemInfo(){
        StringBuilder fullSystemInfo = new StringBuilder();
        fullSystemInfo.append(getOsInfo()).append(lineSeparator).append(lineSeparator).
            append(printMemory()).append(lineSeparator).append(lineSeparator).
            append(getHardwareInfo()).append(lineSeparator);

        return fullSystemInfo.toString();
    }


    /**
     * OS info
     * @return
     */
    private static String getOsInfo(){
        StringBuilder fullOsInfo = new StringBuilder();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        fullOsInfo.append("OS: ").append(operatingSystem.getFamily().toString() + " " + operatingSystem.getVersionInfo().getVersion().toString()).append(lineSeparator).append(lineSeparator).
            append("File System: ").append(String.format(Locale.ROOT, " File Descriptors: %d/%d", operatingSystem.getFileSystem().getOpenFileDescriptors(),
                operatingSystem.getFileSystem().getMaxFileDescriptors()));

        for (OSFileStore fs : operatingSystem.getFileSystem().getFileStores()) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            fullOsInfo.append(" " + String.format(Locale.ROOT,
                        " %s (%s) [%s] %s of %s free (%.1f%%), %s of %s files free (%.1f%%) is %s "
                                + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                                + " and is mounted at %s",
                        fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                        FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                        FormatUtil.formatValue(fs.getFreeInodes(), ""), FormatUtil.formatValue(fs.getTotalInodes(), ""),
                        100d * fs.getFreeInodes() / fs.getTotalInodes(), fs.getVolume(), fs.getLogicalVolume(),
                        fs.getMount()) + ";");
        }

        fullOsInfo.append(lineSeparator).append(lineSeparator).append("Processes: ").append(operatingSystem.getProcesses().toArray().length).append(lineSeparator).append(lineSeparator).
                append("Services: ").append(operatingSystem.getServices().toArray().length);
        return fullOsInfo.toString();
    }

    /**
     * Memory info
     * @return
     */
    private static String printMemory() {
        GlobalMemory  globalMemory = systemInfo.getHardware().getMemory();
        StringBuilder memoryInfo = new StringBuilder();
        memoryInfo.append("RAM Memory: ").append(globalMemory.toString()).append(lineSeparator).append(lineSeparator);
        VirtualMemory vm = globalMemory.getVirtualMemory();
        memoryInfo.append("Virtual Memory: ").append(vm.toString()).append(lineSeparator).append(lineSeparator);
        List<PhysicalMemory> pmList = globalMemory.getPhysicalMemory();
        if (!pmList.isEmpty()) {
            memoryInfo.append("Memory Data: ");
            for (PhysicalMemory pm : pmList) {
                memoryInfo.append("\t" + pm.toString());
            }
        }
        return memoryInfo.toString();
    }

    /**
     * Hardware info
     * @return
     */
    private static String getHardwareInfo(){
        StringBuilder fullHardwareInfo = new StringBuilder();
        HardwareAbstractionLayer hardwareLayer = systemInfo.getHardware();
        fullHardwareInfo.append("Motherboard: ").append(hardwareLayer.getComputerSystem().getBaseboard().getModel()).append(lineSeparator).append(lineSeparator).
            append("Motherboard Model: ").append(hardwareLayer.getComputerSystem().getModel()).append(lineSeparator).append(lineSeparator).
            append("Disks: ").append(lineSeparator);
        int i = 0;
        for (HWDiskStore disk : hardwareLayer.getDiskStores()) {
            fullHardwareInfo.append("\tDisk " + i + ": " + disk.toString().replace("\n",lineSeparator+"\t      "));
            i++;

            List<HWPartition> partitions = disk.getPartitions();
            for (HWPartition part : partitions) {
                fullHardwareInfo.append(" |-- " + part.toString().replace("\n",lineSeparator+"\t"));
            }
        }

        fullHardwareInfo.append(lineSeparator).append(lineSeparator).append("Network IFs: ").append(lineSeparator);
        if (hardwareLayer.getNetworkIFs().isEmpty()) {
            fullHardwareInfo.append("\tUnknown");
        } else {
            i = 0;
            for (NetworkIF net : hardwareLayer.getNetworkIFs()) {
                fullHardwareInfo.append(lineSeparator).append("\tIF " + i + ": "  + net.toString().replace("\n",lineSeparator+"\t    "));
                i++;
            }
        }

        fullHardwareInfo.append(lineSeparator).append(lineSeparator).append("Graphics Cards: ").append(lineSeparator).append(lineSeparator);
        if (hardwareLayer.getGraphicsCards().isEmpty()) {
            fullHardwareInfo.append("\tNone detected.");
        } else {
            i = 0;
            for (GraphicsCard card : hardwareLayer.getGraphicsCards()) {
                fullHardwareInfo.append("\tCard " + i + ": " + String.valueOf(card).replace("\n",lineSeparator+"\t         "));
                i++;
            }
        }

        fullHardwareInfo.append(lineSeparator).append(lineSeparator).append("Displays: ").append(lineSeparator).append(lineSeparator);
        i = 0;
        for (Display display : hardwareLayer.getDisplays()) {
            fullHardwareInfo.append("\tDisplay " + i + ": ");

            fullHardwareInfo.append(String.valueOf(display).replace("\n",lineSeparator+"\t           "));
            i++;
        }
        return fullHardwareInfo.toString();
    }

}
