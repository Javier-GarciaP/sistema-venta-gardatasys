; Script para Inno Setup - GardataSys (Versión Final con Permisos)

[Setup]
AppId={{C6E2E89D-8D5F-4E7B-B4A1-F9D9F8E8C8D9}
AppName=GardataSys - Sistema de Ventas
AppVersion=1.0
AppPublisher=Javier Garcia
DefaultDirName={autopf}\GardataSys
DefaultGroupName=GardataSys
AllowNoIcons=yes
OutputDir=.\Output
OutputBaseFilename=GardataSys_Setup
Compression=lzma
SolidCompression=yes
WizardStyle=modern
PrivilegesRequired=admin
SetupIconFile=icon.ico

[Languages]
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "SistemaVenta.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "SistemaVenta.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "icon.ico"; DestDir: "{app}"; Flags: ignoreversion
Source: "init.sql"; DestDir: "{app}"; Flags: ignoreversion
Source: "productos-init.sql"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs

[Dirs]
; ESTA LINEA ES LA CLAVE: Da permisos de lectura/escritura a todos los usuarios en la carpeta de la app
Name: "{app}"; Permissions: users-full

[Icons]
Name: "{group}\GardataSys"; Filename: "{app}\SistemaVenta.bat"; IconFilename: "{app}\icon.ico"
Name: "{commondesktop}\GardataSys"; Filename: "{app}\SistemaVenta.bat"; IconFilename: "{app}\icon.ico"; Tasks: desktopicon

[Run]
Filename: "{app}\SistemaVenta.bat"; Description: "{cm:LaunchProgram,GardataSys}"; Flags: nowait postinstall skipifsilent
