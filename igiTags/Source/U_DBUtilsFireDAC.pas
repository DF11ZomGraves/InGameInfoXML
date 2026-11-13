unit U_DBUtilsFireDAC;

interface

uses
  SysUtils, Classes, Forms, DB, uADStanIntf, uADCompClient,
  cxClasses, cxCustomData, cxGridCustomTableView, cxGridTableView, cxGridDBTableView, cxGrid;

type
  ECustomDataAssignException = class(Exception);

  TcxFieldPropertiesRec = record
    _GridView: TcxGridDBTableView;
    AlignmentHorz: TAlignment; // taLeftJustify
    AlignmentVert: TcxAlignmentVert; // vaTop
    DisplayFormat: string;
    FoooterFormat: string;
    SummaryKind: TcxSummaryKind; // skNone
    SummaryColumnIndex: Integer; // -1
    SummaryDisplayText: string;
  end;

  TDBFieldMappingRec = record
    MappingQuery: TADQuery;
    KeyField: string;
    DisplayField: string;
    GroupField: string;
    GroupIndexField: string;
  end;

  TcxGridViewHelper = class
    class procedure SetTitleAlignment(AGridView: TcxGridDBTableView;
      AAlignHorz: TAlignment; AAlignVert: TcxAlignmentVert = vaTop);
    class procedure DeleteAllColumns(AGridView: TcxGridDBTableView);
    class procedure CreateFields(AFieldPropertiesRec: TcxFieldPropertiesRec; AMappingRec: TDBFieldMappingRec);
  end;

procedure ConnectToMsAccDB(AConnection: TADConnection; const ADBFileName: string);

implementation

procedure ConnectToMsAccDB(AConnection: TADConnection; const ADBFileName: string);
begin
  with AConnection.Params do begin
    Clear;
    Add('Database=' + ADBFileName);
    Add('DriverID=MSAcc');
  end;
  with AConnection, FormatOptions do begin
    Connected := True;
    Open;
  end;
end;

{ TcxGridViewHelper }

class procedure TcxGridViewHelper.CreateFields(AFieldPropertiesRec: TcxFieldPropertiesRec;
  AMappingRec: TDBFieldMappingRec);
var
  oSource: TDataSource;
  oDataSet: TDataSet;
  oColumn: TcxGridDBColumn;
  oField: TField;
  oSummaryItems: TcxDataFooterSummaryItems;
  sMappingField, sDisplayText: string;
  i, nGroupIndex: Integer;
  bMapping: Boolean;
begin
  with AFieldPropertiesRec do begin
    if _GridView = nil then
      raise ECustomDataAssignException.Create('GridView is Empty!');
    oSource := _GridView.DataController.DataSource;
    if oSource = nil then
      raise ECustomDataAssignException.CreateFmt('DataSource [%s] not assigned!', [_GridView.Name]);
  end;
  oDataSet := oSource.DataSet;
  if oDataSet = nil then
    raise ECustomDataAssignException.CreateFmt('DataSet [%s] not assigned!', [oSource.Name]);
  if not oDataSet.Active then
    raise ECustomDataAssignException.CreateFmt('DataSet [%s] not opened!', [oDataSet.Name]);
  with AMappingRec do
    bMapping := ((MappingQuery <> nil) and (KeyField <> '') and (DisplayField <> ''));
  with AFieldPropertiesRec, _GridView do try
    BeginUpdate;
    ClearItems;
    oSummaryItems := DataController.Summary.FooterSummaryItems;
    oSummaryItems.Clear;
    for i := 0 to oDataSet.FieldCount - 1 do begin
      oField := oDataSet.Fields[i];
      if not oField.Visible then
        Continue;
      oColumn := CreateColumn;
      with oColumn do begin
        DataBinding.FieldName := oField.FieldName;
        HeaderAlignmentHorz := AlignmentHorz;
        HeaderAlignmentVert := AlignmentVert;
      end;
      sMappingField := oField.FieldName;
      sDisplayText := '';
      if bMapping then
        with AMappingRec, MappingQuery, oColumn do begin
          if Active and Locate(KeyField, sMappingField, []) then begin
            sDisplayText := FieldByName(DisplayField).AsString;
            Options.Grouping := Boolean(FieldByName(GroupField).AsInteger);
            nGroupIndex := FieldByName(GroupIndexField).AsInteger;
            GroupIndex := nGroupIndex;
            if nGroupIndex >= 0 then
              Visible := False;
          end;
          if sDisplayText <> '' then
            Caption := sDisplayText;
        end;
      if i = SummaryColumnIndex then
        with TcxGridDBTableSummaryItem(oSummaryItems.Add) do begin
          FieldName := sMappingField;
          Format := SummaryDisplayText;
          Column := oColumn;
          Kind := skCount;
          Continue;
        end;
      if SummaryKind = skNone then
        Continue;
      if not (oField is TNumericField) then
        Continue;
      with TcxGridDBTableSummaryItem(oSummaryItems.Add) do begin
        FieldName := oField.FieldName;
        Format := FoooterFormat;
        Column := oColumn;
        Kind := SummaryKind;
      end;
    end;
  finally
    EndUpdate;
  end;
end;

class procedure TcxGridViewHelper.DeleteAllColumns(AGridView: TcxGridDBTableView);
begin
  with AGridView do
    while ColumnCount > 0 do
      Columns[0].Destroy;
end;

class procedure TcxGridViewHelper.SetTitleAlignment(AGridView: TcxGridDBTableView;
  AAlignHorz: TAlignment; AAlignVert: TcxAlignmentVert);
var
  i: Integer;
begin
  with AGridView do
    for i := 0 to ColumnCount - 1 do
      with Columns[i] do begin
        HeaderAlignmentHorz := AAlignHorz;
        HeaderAlignmentVert := AAlignVert;
      end;
end;


end.
