/* empty css             *//* empty css                  *//* empty css                        *//* empty css                  *//* empty css                   *//* empty css                  */import{d as C,j as M,k as l,l as H,m as N,h as u,E as R,o as h,a as o,g as t,f as x,n as I,p as L,q as E,t as P,u as F,x as j,y as W,c as q,b as G,w as J,F as K,z as O,A as Q,B as X,C as Y,D as Z}from"./index-x-5zwZLn.js";/* empty css                 */import{a as ee,g as te,d as ne}from"./personInCharge-B2et5bYU.js";const le={class:"grid gap-4"},ae={class:"dialog-footer"},oe=C({__name:"NoticeDialog",emits:["RefreshTable"],setup(T,{expose:v,emit:D}){const c=M(),p=l(!1),V=D,a=H({uid:void 0,title:"",content:""}),f=()=>{a.title===""||a.content===""?I.warning("内容不能为空!"):ee(c.user.uid,a.title,a.content).then(g=>{p.value=!g,V("RefreshTable")})};return v({isShowDialog:p}),(g,n)=>{const r=L,m=E,b=R;return h(),N(b,{modelValue:p.value,"onUpdate:modelValue":n[3]||(n[3]=i=>p.value=i),title:"编辑公告",width:"500"},{footer:u(()=>[o("div",ae,[t(m,{onClick:n[2]||(n[2]=()=>{p.value=!1})},{default:u(()=>n[4]||(n[4]=[x("取消 ")])),_:1}),t(m,{type:"primary",onClick:f},{default:u(()=>n[5]||(n[5]=[x("确定")])),_:1})])]),default:u(()=>[o("div",le,[o("div",null,[t(r,{modelValue:a.title,"onUpdate:modelValue":n[0]||(n[0]=i=>a.title=i),placeholder:"标题"},null,8,["modelValue"])]),o("div",null,[t(r,{modelValue:a.content,"onUpdate:modelValue":n[1]||(n[1]=i=>a.content=i),autosize:{minRows:2,maxRows:4},type:"textarea",placeholder:"内容"},null,8,["modelValue"])])])]),_:1},8,["modelValue"])}}}),ie={class:"announcement-management-view-header flex h-10 items-center justify-between border-b-2 border-solid border-inherit px-2"},ue={class:"personnel-manage-view-main px-2 pt-1"},se={class:"dialog-footer"},re={class:"personnel-manage-view-pagination h-10 py-1 md:pl-2"},we=C({__name:"AnnouncementManagementView",setup(T){const{windowWidth:v,windowHeight:D}=P(F()),c=l(null),p=l(null),V=l([]),a=l(0),f=l(!1),g=l(v.value<=768?"prev, pager, next":"total, prev, pager, next"),n=l(0),r=l(v.value<=768?10:15),m=l(1),b=l(v.value<=768?5:9),i=l(!1),k=l(0);j(()=>{a.value=c.value.clientHeight-42-2-40,y()}),W([v,D],()=>{a.value=c.value.clientHeight-42-2-40,v.value<=768?(g.value="prev, pager, next",r.value=10,b.value=5):(g.value="total, prev, pager, next",r.value=15,b.value=9)});const y=(_=r.value,e=m.value)=>{f.value=!0,te(e,_).then(d=>{d!==null&&(V.value=d.tableData,n.value=d.total,m.value=d.pageNumber),f.value=!1})},A=()=>{p.value.isShowDialog=!0},S=()=>{ne(k.value).then(_=>{i.value=!_,_&&y()})};return(_,e)=>{const d=E,w=Z,z=O,U=R,B=Q,$=X;return h(),q(K,null,[o("div",{class:"announcement-management-view-container h-full rounded-lg bg-base-100",ref_key:"AnnouncementManagementViewRef",ref:c},[o("div",ie,[e[8]||(e[8]=o("h1",{class:"text-lg font-semibold"},"公告管理",-1)),o("div",null,[t(d,{type:"primary",icon:G(Y),onClick:A},{default:u(()=>e[7]||(e[7]=[x("发布公告")])),_:1},8,["icon"])])]),o("div",ue,[J((h(),N(z,{data:V.value,height:a.value,"highlight-current-row":"",stripe:""},{default:u(()=>[t(w,{align:"center",label:"创建人",width:"auto",prop:"name"}),t(w,{align:"center",label:"标题",width:"auto",prop:"title"}),t(w,{align:"center",label:"内容",width:"auto",prop:"content"}),t(w,{align:"center",label:"创建时间",width:"auto",prop:"creationTime"}),t(w,{align:"center",label:"操作","min-width":"105px"},{default:u(s=>[t(d,{circle:"",icon:"Delete",type:"danger",onClick:()=>{i.value=!0,k.value=s.row.announcementId}},null,8,["onClick"])]),_:1})]),_:1},8,["data","height"])),[[$,f.value]]),t(U,{modelValue:i.value,"onUpdate:modelValue":e[2]||(e[2]=s=>i.value=s),title:"Warning",width:"500","align-center":""},{footer:u(()=>[o("div",se,[t(d,{onClick:e[0]||(e[0]=()=>{i.value=!1})},{default:u(()=>e[9]||(e[9]=[x("取消 ")])),_:1}),t(d,{type:"primary",onClick:e[1]||(e[1]=s=>S())},{default:u(()=>e[10]||(e[10]=[x("确定")])),_:1})])]),default:u(()=>[e[11]||(e[11]=o("span",null,"是否删除公告？",-1))]),_:1},8,["modelValue"])]),o("div",re,[t(B,{"current-page":m.value,"onUpdate:currentPage":e[3]||(e[3]=s=>m.value=s),"page-size":r.value,"onUpdate:pageSize":e[4]||(e[4]=s=>r.value=s),layout:g.value,"page-sizes":[10,15,20,30,40],"pager-count":b.value,total:n.value,onCurrentChange:e[5]||(e[5]=s=>y(void 0,m.value))},null,8,["current-page","page-size","layout","pager-count","total"])])],512),t(oe,{ref_key:"NoticeDialogRef",ref:p,onRefreshTable:e[6]||(e[6]=s=>y(r.value,1))},null,512)],64)}}});export{we as default};
