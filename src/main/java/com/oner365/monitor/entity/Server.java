package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.oner365.monitor.entity.server.Cpu;
import com.oner365.monitor.entity.server.Jvm;
import com.oner365.monitor.entity.server.Mem;
import com.oner365.monitor.entity.server.Sys;
import com.oner365.monitor.entity.server.SysFile;
import com.oner365.util.Arith;
import com.oner365.util.DataUtils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 服务器相关信息
 *
 * @author zhaoyong
 */
public class Server implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final int WAIT_SECOND = 1000;

  /**
   * CPU相关信息
   */
  private Cpu cpu = new Cpu();

  /**
   * 內存相关信息
   */
  private Mem mem = new Mem();

  /**
   * JVM相关信息
   */
  private Jvm jvm = new Jvm();

  /**
   * 服务器相关信息
   */
  private Sys sys = new Sys();

  /**
   * 磁盘相关信息
   */
  private List<SysFile> sysFiles = new LinkedList<>();

  public Cpu getCpu() {
    return cpu;
  }

  public void setCpu(Cpu cpu) {
    this.cpu = cpu;
  }

  public Mem getMem() {
    return mem;
  }

  public void setMem(Mem mem) {
    this.mem = mem;
  }

  public Jvm getJvm() {
    return jvm;
  }

  public void setJvm(Jvm jvm) {
    this.jvm = jvm;
  }

  public Sys getSys() {
    return sys;
  }

  public void setSys(Sys sys) {
    this.sys = sys;
  }

  public List<SysFile> getSysFiles() {
    return sysFiles;
  }

  public void setSysFiles(List<SysFile> sysFiles) {
    this.sysFiles = sysFiles;
  }

  public void copyTo() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();

    setCpuInfo(hal.getProcessor());

    setMemInfo(hal.getMemory());

    setSysInfo();

    setJvmInfo();

    setOperatingSystem(si.getOperatingSystem());
  }

  /**
   * 设置CPU信息
   */
  private void setCpuInfo(CentralProcessor processor) {
    // CPU信息
    long[] prevTicks = processor.getSystemCpuLoadTicks();
    Util.sleep(WAIT_SECOND);
    long[] ticks = processor.getSystemCpuLoadTicks();
    long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
    long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
    long softIrq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
    long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
    long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
    long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
    long ioWait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
    long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
    long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;
    cpu.setCpuNum(processor.getLogicalProcessorCount());
    cpu.setTotal(totalCpu);
    cpu.setSys(cSys);
    cpu.setUsed(user);
    cpu.setWait(ioWait);
    cpu.setFree(idle);
  }

  /**
   * 设置内存信息
   */
  private void setMemInfo(GlobalMemory memory) {
    mem.setTotal(memory.getTotal());
    mem.setUsed(memory.getTotal() - memory.getAvailable());
    mem.setFree(memory.getAvailable());
  }

  /**
   * 设置服务器信息
   */
  private void setSysInfo() {
    Properties props = System.getProperties();
    sys.setComputerName(DataUtils.getHostName());
    sys.setComputerIp(DataUtils.getLocalhost());
    sys.setOsName(props.getProperty("os.name"));
    sys.setOsArch(props.getProperty("os.arch"));
    sys.setUserDir(props.getProperty("user.dir"));
  }

  /**
   * 设置Java虚拟机
   */
  private void setJvmInfo() {
    Properties props = System.getProperties();
    jvm.setTotal(Runtime.getRuntime().totalMemory());
    jvm.setMax(Runtime.getRuntime().maxMemory());
    jvm.setFree(Runtime.getRuntime().freeMemory());
    jvm.setVersion(props.getProperty("java.version"));
    jvm.setHome(props.getProperty("java.home"));
  }

  /**
   * 设置磁盘信息
   */
  private void setOperatingSystem(OperatingSystem os) {
    FileSystem fileSystem = os.getFileSystem();
    List<OSFileStore> list = fileSystem.getFileStores();
    list.forEach(fs -> {
      long free = fs.getUsableSpace();
      long total = fs.getTotalSpace();
      long used = total - free;
      SysFile sysFile = new SysFile();
      sysFile.setDirName(fs.getMount());
      sysFile.setSysTypeName(fs.getType());
      sysFile.setTypeName(fs.getName());
      sysFile.setTotal(DataUtils.convertFileSize(total));
      sysFile.setFree(DataUtils.convertFileSize(free));
      sysFile.setUsed(DataUtils.convertFileSize(used));
      sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
      sysFiles.add(sysFile);
    });
  }

}
