# what is GRUB?
  GNU GRUB: Grand Unified Bootloader
  a multiboot boot loader (boot loader is the first software program that runs when a
    computer starts; it loads and transfer control to OS kernel software)

# linux boot process
  there are 6 high-level stages
  1. BIOS: Basic Input/Output System
    a program that a computer uses to get the computer started after you turn it on. it manages
      data flow between OS and attached devices such as hard disk, video adapter, keyboard, mouse,
      and printer
  2. MBR: Master Boot Record
    the first sector of hard disk that identifies how and where the OS is located so that it
      can be loaded into main storage or RAM
  3. GRUB: GRand Unified Bootloader
    a package that supports multiple OS and allow users to select among them during boot-up
  4. Kernel: kernel
    the core of OS that provides basic services for all other parts of the OS
  5. Init: init
  6. Runlevel:
    an operating state on UNIX, which are numbered from 0 to 6. runlevel programs are executed
      from /etc/rc.d/rc[0-6].d/

# how to change the default run level in linux?
  init: command that temporarily change run level, ex. init 3 (default level is 5)
  to change permanently: edit /etc/inittab

# how to list or enable services that are enabled at a particular run level?
  list service: chkconfig --list | grep 5:on
  enable service: chkconfig <name_service> on --level 3

# PXE: Preboot Execution Environment
  an execution environment before boot, which requires two things:
  1. the network interface must support PXE client funcationality
    i.e. need to set boot device in BIOS to network interface
  2. the PXE server must provide DHCP and TFTP services
    1.1. DHCP: provide network configuration parameters and let the client know where TFTP is
    1.2 TFTP: provide the client with the boot loader and the kernel file
  you need boot loader and kernel to either boot up or install OS on a computer
  PXE is used when you need to install OS on a computer via network

