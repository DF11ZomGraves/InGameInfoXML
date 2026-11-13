object dmDB: TdmDB
  OldCreateOrder = False
  OnCreate = DataModuleCreate
  OnDestroy = DataModuleDestroy
  Height = 239
  Width = 359
  object ADConnection1: TADConnection
    LoginPrompt = False
    Left = 72
    Top = 40
  end
  object ADQuery1: TADQuery
    Connection = ADConnection1
    Left = 192
    Top = 64
  end
  object DataSource1: TDataSource
    DataSet = ADQueryTag
    Left = 296
    Top = 136
  end
  object ADPhysMSAccessDriverLink1: TADPhysMSAccessDriverLink
    Left = 112
    Top = 112
  end
  object ADQueryTag: TADQuery
    Connection = ADConnection1
    Left = 224
    Top = 136
  end
  object ADGUIxWaitCursor1: TADGUIxWaitCursor
    ScreenCursor = gcrHourGlass
    Left = 112
    Top = 176
  end
  object ADQueryMapping: TADQuery
    Connection = ADConnection1
    Left = 272
    Top = 40
  end
end
