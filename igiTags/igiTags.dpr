program igiTags;

uses
  Forms,
  fm_Main in 'Source\fm_Main.pas' {frmMain},
  dm_DB in 'Source\dm_DB.pas' {dmDB: TDataModule},
  U_DBUtilsFireDAC in 'Source\U_DBUtilsFireDAC.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.MainFormOnTaskbar := True;
  Application.CreateForm(TdmDB, dmDB);
  Application.CreateForm(TfrmMain, frmMain);
  Application.Run;
end.
