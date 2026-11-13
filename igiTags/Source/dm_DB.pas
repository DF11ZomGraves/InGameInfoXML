unit dm_DB;

interface

uses
  SysUtils, Classes, Forms, DB, uADStanIntf, uADStanOption, uADStanError, uADGUIxIntf,
  uADPhysIntf, uADStanDef, uADStanPool, uADStanAsync, uADPhysManager,
  uADStanParam, uADDatSManager, uADDAptIntf, uADDAptManager, uADCompDataSet, uADCompClient,
  uADPhysODBCBase, uADPhysMSAcc, uADGUIxFormsWait, uADCompGUIx;

type
  TdmDB = class(TDataModule)
    ADConnection1: TADConnection;
    ADQuery1: TADQuery;
    DataSource1: TDataSource;
    ADPhysMSAccessDriverLink1: TADPhysMSAccessDriverLink;
    ADQueryTag: TADQuery;
    ADGUIxWaitCursor1: TADGUIxWaitCursor;
    ADQueryMapping: TADQuery;
    procedure DataModuleCreate(Sender: TObject);
    procedure DataModuleDestroy(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

  TADQueryHelper = class helper for TADQuery
    function GetInt(const AFieldName: string): Integer;
    procedure SetInt(const AFieldName: string; const AValue: Integer);
    function GetStr(const AFieldName: string): string;
    procedure SetStr(const AFieldName: string; const AValue: string);
    function GetBool(const AFieldName: string): Boolean;
    procedure SetBool(const AFieldName: string; const AValue: Boolean);
  public
    property I[const AFieldName: string]: Integer read GetInt write SetInt;
    property S[const AFieldName: string]: string read GetStr write SetStr;
    property B[const AFieldName: string]: Boolean read GetBool write SetBool;
  end;

var
  dmDB: TdmDB;

implementation

uses
  U_DBUtilsFireDAC;

{$R *.dfm}

procedure TdmDB.DataModuleCreate(Sender: TObject);
var
  sAccDB: string;
begin
  with Application do begin
    sAccDB := ChangeFileExt(ExeName, '.accdb');  // Office 2007
    if not FileExists(sAccDB) then
      sAccDB := ChangeFileExt(ExeName, '.mdb');  // Office 2002-2003
    try
      ConnectToMsAccDB(ADConnection1, sAccDB);
    except
      on E: Exception do begin
        MessageBox(PChar('Connect database failed: ' + E.Message), PChar(Title), 16);
        Terminate;
      end;
    end;
  end;
end;

procedure TdmDB.DataModuleDestroy(Sender: TObject);
begin
  ADQuery1.Close;
  ADQueryTag.Close;
  ADQueryMapping.Close;
  ADConnection1.Close;
end;

{ TADQueryHelper }

function TADQueryHelper.GetBool(const AFieldName: string): Boolean;
begin
  Result := FieldByName(AFieldName).AsBoolean;
end;

function TADQueryHelper.GetInt(const AFieldName: string): Integer;
begin
  Result := FieldByName(AFieldName).AsInteger;
end;

function TADQueryHelper.GetStr(const AFieldName: string): string;
begin
  Result := FieldByName(AFieldName).AsString;
end;

procedure TADQueryHelper.SetBool(const AFieldName: string;
  const AValue: Boolean);
begin
  FieldByName(AFieldName).AsBoolean := AValue;
end;

procedure TADQueryHelper.SetInt(const AFieldName: string;
  const AValue: Integer);
begin
  FieldByName(AFieldName).AsInteger := AValue;
end;

procedure TADQueryHelper.SetStr(const AFieldName, AValue: string);
begin
  FieldByName(AFieldName).AsString := AValue;
end;

end.
