// 登陆表单
export interface LoginReqForm {
  uid: number | string
  password: string
  isRememberMe: number
}

// 更新密码表单(未登陆)
export interface UpdatePasswordReqFormF {
  uid: number | string
  email: string
  captcha: string
  newPassword: string
}

// 更新密码表单(已登陆)
export interface UpdatePasswordReqFormT {
  oldPassword: string
  newPassword: string
  repeatPassword: string
}

// 用户信息
export interface userInfo {
  uid: number
  studentClass: string
  name: string
  email: string
  joinDate: string
  questionNumber: number
  authority: string
  avatarPath: string
  ranking: number
}

// 路由表
export interface routerListReq {
  id: number
  path: string
  name: string
  meta: {
    icon: string
    title: string
  }
}

// 概览数据
export interface overViewDataReq {
  totalCount: number
  inTrainingCount: number
  graduatedCount: number
  withdrawalCount: number
}

// 公告数据
export interface announcementDataReq {
  title: string
  content: string
  creationTime: string
}

// 排名
export interface rankTableDataReq {
  uid: number
  name: string
  studentClass: string
  questionNumber: number
}

// 打卡记录表单
export interface clockInReqForm {
  pictureFile: File
  number: number
  logs: string
}

// 打卡记录更新表单
export interface clockInUpdateReqForm {
  recordsId: number
  pictureFile: File
  number: number
  logs: string
}

// 个人打卡记录数据
export interface ClockInDataReq {
  total: number
  pageSize: number
  tableData: string | Array<ClockInTableDataReq>
}

// 个人打卡记录表单数据
export interface ClockInTableDataReq {
  recordsId: number
  submitTime: string
  updateTime: string
  number: number
  logs: string
  picturePath: string
}

// 周报表单
export interface weeklyDataReqForm {
  weeklyTitle: string
  weeklyContent: string
}

// 个人周报记录
export interface WeeklyDataReq {
  total: number
  pageSize: number
  tableData: string | Array<WeeklyTableDataReq>
}

// 个人周报记录表单数据
export interface WeeklyTableDataReq {
  weeklyId: number
  fileName: string
  submitTime: string
  filePath: number
  evaluate: string
}

// 负责人员数据
export interface TraineeDataReq {
  total: number
  pageSize: number
  tableData: string | Array<TraineeTableDataReq>
}

// 负责人员表格数据
export interface TraineeTableDataReq {
  uid: number
  studentClass: string
  name: string
  sex: string
  state: number
  clockInState: number
  weeklyState: number
}

// 人员状态表单
export interface PersonnelStateReqForm {
  uid: number
  personnelState: number
}

// 未打卡人员表格数据
export interface clockInTableFDataReq {
  uid: number
  studentClass: string
  name: string
}

// 题量分布数据
export interface questionQuantityDistributionDataReq {
  number: Array<number>
  totalRecords: Array<number>
}

// 打卡记录数据
export interface ClockInRecordDataReq {
  total: number
  pageSize: number
  currentPage: number
  tableData: string | Array<ClockInRecordTableDataReq>
}

// 打卡记录表格数据
export interface ClockInRecordTableDataReq {
  recordsId: number
  uid: number
  studentClass: string
  name: string
  number: number
  logs: string
  submitTime: string
  updateTime: string
  picturePath: string
}

// 未提交周报人员表格数据
export interface weeklyTableFDataReq {
  uid: number
  studentClass: string
  name: string
}

// 周报记录数据
export interface weeklyRecordDataReq {
  total: number
  pageSize: number
  currentPage: number
  tableData: string | Array<weeklyRecordTableDataReq>
}

// 周报记录表格数据
export interface weeklyRecordTableDataReq {
  weeklyId: number
  uid: number
  studentClass: string
  name: string
  fileName: number
  submitTime: string
  evaluate: string
  filePath: string
}

// 评价周报表单
export interface EvaluateWeeklyForm {
  weeklyId: number
  evaluateContent: string
}

// 人员数据
export interface PersonnelDataReq {
  total: number
  pageSize: number
  pageNumber: number
  tableData: string | Array<PersonnelTableDataReq>
}

// 人员表格数据
export interface PersonnelTableDataReq {
  uid: number
  studentClass: string
  sex: string
  name: string
  principal: string
  questionNumber: number
  authority: number
  state: number
}

export interface UpdateUserInfoReqFormAdmin {
  uid: number
  studentClass: string
  sex: string
  name: string
  authority: number
  state: number
}

// 人员数据
export interface AnnouncementTableDaraReq {
  total: number
  pageSize: number
  pageNumber: number
  tableData: string | Array<AnnouncementTableDataReq>
}

export interface AnnouncementTableDataReq {
  announcementId: number
  name: string
  title: string
  content: string
  creationTime: string
}

export interface AnnouncementFormReq {
  uid: number
  title: string
  content: string
}
