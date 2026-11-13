unit fm_Main;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, cxGraphics, cxControls, cxLookAndFeels, cxLookAndFeelPainters,
  dxSkinsCore, dxSkinsDefaultPainters, dxSkinscxPCPainter, cxContainer,
  cxEdit, dxLayoutcxEditAdapters, dxLayoutContainer, cxStyles,
  cxCustomData, cxFilter, cxData, cxDataStorage, cxNavigator, DB, cxDBData,
  cxGridLevel, cxClasses, cxGridCustomView, cxGridCustomTableView,
  cxGridTableView, cxGridDBTableView, cxGrid, cxTextEdit, cxMaskEdit,
  cxDropDownEdit, dxLayoutControl;

type
  TfrmMain = class(TForm)
    dxLayoutControl1Group_Root: TdxLayoutGroup;
    dxLayoutControl1: TdxLayoutControl;
    cxComboBoxMCVer: TcxComboBox;
    dxLayoutControl1Item1: TdxLayoutItem;
    cxComboBoxModVer: TcxComboBox;
    dxLayoutControl1Item2: TdxLayoutItem;
    cxGrid1DBTableView1: TcxGridDBTableView;
    cxGrid1Level1: TcxGridLevel;
    cxGrid1: TcxGrid;
    dxLayoutControl1Item3: TdxLayoutItem;
    dxLayoutControl1Group1: TdxLayoutAutoCreatedGroup;
    cxStyleRepository1: TcxStyleRepository;
    cxStyle1: TcxStyle;
    cxStyle2: TcxStyle;
    procedure FormCreate(Sender: TObject);
    procedure cxComboBoxMCVerPropertiesChange(Sender: TObject);
    procedure cxGrid1DBTableView1ColumnPosChanged(Sender: TcxGridTableView;
      AColumn: TcxGridColumn);
    procedure FormShow(Sender: TObject);
  private
    { Private declarations }
    procedure ExpandTable;
    function DataSetConnected: Boolean;
  public
    { Public declarations }
  end;

var
  frmMain: TfrmMain;

implementation

uses
  dm_DB, U_DBUtilsFireDAC;

{$R *.dfm}

procedure TfrmMain.cxComboBoxMCVerPropertiesChange(Sender: TObject);
var
  ss, sFilter: string;
begin
  if not DataSetConnected then
    Exit;
  with dmDB do begin
    with ADQueryTag do begin
      Filtered := False;
      Filter := '';
    end;
    with ADQueryMapping, cxComboBoxMCVer do
      if (ItemIndex > 0) and Locate('DisplayText', Text) then begin
        ss := S['MappingField'];
        sFilter := Format('%s = True', [ss]);
      end;
    with cxComboBoxModVer do
      if (ItemIndex > 0) then begin
        ss := Format('(ModVer is null or ModVer <= %d)', [ItemIndex]);
        if sFilter <> '' then
          ss := ' and ' + ss;
        sFilter := sFilter + ss;
      end;
    if sFilter <> '' then
      with ADQueryTag do begin
        Filter := sFilter;
        Filtered := True;
      end;
  end;
  ExpandTable;
end;

procedure TfrmMain.cxGrid1DBTableView1ColumnPosChanged(
  Sender: TcxGridTableView; AColumn: TcxGridColumn);
begin
  if DataSetConnected then
    ExpandTable;
end;

function TfrmMain.DataSetConnected: Boolean;
begin
  with dmDB.ADQueryTag do
    Result := Active and not IsEmpty;
end;

procedure TfrmMain.ExpandTable;
begin
  with cxGrid1DBTableView1, DataController do begin
    Groups.FullExpand;
    ApplyBestFit();
    GotoFirst;
  end;
end;

procedure TfrmMain.FormCreate(Sender: TObject);
var
  oDBFieldMappingRec: TDBFieldMappingRec;
  ocxFieldPropertiesRec: TcxFieldPropertiesRec;
begin
  with dmDB.ADQuery1, cxComboBoxModVer do begin
    Open('select VerName from t_ModVersion order by VerID');
    while not Eof do begin
      Properties.Items.Add(S['VerName']);
      Next;
    end;
    if RecordCount > 0 then
      ItemIndex := 0;
    Close;
  end;
  with dmDB.ADQuery1, cxComboBoxMCVer do begin
    Open('select DisplayText from t_DBFieldMapping where Filter = True');
    while not Eof do begin
      Properties.Items.Add(S['DisplayText']);
      Next;
    end;
    if RecordCount > 0 then
      ItemIndex := 0;
    Close;
  end;
  with dmDB, ADQueryMapping, oDBFieldMappingRec do begin
    Open('select MappingField, DisplayText, CanGroup, GroupIndex from t_DBFieldMapping');
    MappingQuery := ADQueryMapping;
    KeyField := 'MappingField';
    DisplayField := 'DisplayText';
    GroupField := 'CanGroup';
    GroupIndexField := 'GroupIndex';
  end;
  with ocxFieldPropertiesRec do begin
    _GridView := cxGrid1DBTableView1;
    AlignmentHorz := taLeftJustify;
    AlignmentVert := vaTop;
    SummaryKind := skCount;
    SummaryColumnIndex := 1;
  end;
  with dmDB.ADQueryTag do begin
    Open('select * from V_Tag');
    FieldByName('ModVer').Visible := False;
  end;
  TcxGridViewHelper.CreateFields(ocxFieldPropertiesRec, oDBFieldMappingRec);
  ExpandTable;
end;

procedure TfrmMain.FormShow(Sender: TObject);
begin
  cxGrid1.SetFocus;
end;

end.
