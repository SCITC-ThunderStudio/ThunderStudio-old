import{u as l,aQ as c,aR as d,n,aT as u}from"./index-x-5zwZLn.js";const i="/admin",y=async a=>{let t=null;const e=l();return await c.get(`${i}/getClockInListF`,{params:{option:a},headers:{"Session-Id":window.localStorage.getItem(void 0),Key:e.aesKey_RSA,Iv:e.aesIv_RSA,Encrypt:"0"}}).then(s=>{if(s.state===200){const r=d.getAESUtils().decrypt(s.data);t=JSON.parse(r)}else n.error(s.message)}).catch(s=>{n.error("获取未打卡人员名单失败:"+s.message)}),t},m=async()=>{let a=null;return await c.get(`${i}/getQuestionQuantityDistribution`,{headers:{"Session-Id":window.localStorage.getItem(void 0),Encrypt:"0"}}).then(t=>{t.state===200?a=t.data:n.error(t.message)}).catch(t=>{n.error("获取题量分布失败:"+t.message)}),a},S=async(a,t,e)=>{let s=null;const r=l();return await c.get(`${i}/getClockInRecord`,{params:{option:a,currentPage:t,pageSize:e},headers:{"Session-Id":window.localStorage.getItem(void 0),Key:r.aesKey_RSA,Iv:r.aesIv_RSA,Encrypt:"0"}}).then(o=>{if(o.state===200){s=o.data;const g=d.getAESUtils().decrypt(s.tableData);s.tableData=JSON.parse(g)}else n.error(o.message)}).catch(o=>{n.error("获取打卡记录失败:"+o.message)}),s},h=async a=>{let t=null;const e=l();return await c.get(`${i}/getWeeklyListF`,{params:{option:a},headers:{"Session-Id":window.localStorage.getItem(void 0),Key:e.aesKey_RSA,Iv:e.aesIv_RSA,Encrypt:"0"}}).then(s=>{if(s.state===200){const r=d.getAESUtils().decrypt(s.data);t=JSON.parse(r)}else n.error(s.message)}).catch(s=>{n.error("获取未提交周报人员名单失败:"+s.message)}),t},w=async a=>{let t=!1;return await c.post(`${i}/uploadTrainingListFile`,{TrainingList:a},{headers:{"Content-Type":"multipart/form-data;charset=UTF-8","Session-Id":window.localStorage.getItem(void 0),Encrypt:"0"}}).then(e=>{e.state===635?(t=!0,n.success(e.message)):n.error(e.message)}).catch(e=>{n.error("上传训练营名单失败:"+e.message)}),t},I=async(a,t,e)=>{let s=null;const r=l();return await c.get(`${i}/getWeeklyRecord`,{params:{option:a,currentPage:t,pageSize:e},headers:{"Session-Id":window.localStorage.getItem(void 0),Key:r.aesKey_RSA,Iv:r.aesIv_RSA,Encrypt:"0"}}).then(o=>{if(o.state===200){s=o.data;const g=d.getAESUtils().decrypt(s.tableData);s.tableData=JSON.parse(g)}else n.error(o.message)}).catch(o=>{n.error("获取周报记录失败:"+o.message)}),s},f=async a=>{let t=!1;return await c.patch(`${i}/evaluateWeekly`,a,{headers:{"Session-Id":window.localStorage.getItem(void 0),Encrypt:"0"}}).then(e=>{e.state===625?(t=!0,n.success(e.message)):n.error(e.message)}).catch(e=>{n.error("周报评价失败"+e.message)}),t},b=async()=>{u({url:`/api/${i}/downloadTrainingListFile`,method:"get",responseType:"blob"}).then(a=>{const t=new Blob([a.data],{type:a.headers["content-type"]}),e=a.headers["content-disposition"].match(/fileName=(.*)/)[1],s=window.URL.createObjectURL(t),r=document.createElement("a");r.style.display="none",r.href=s;const o=a.headers["content-disposition"]||`attachment;fileName=${e}`;r.setAttribute("download",decodeURI(o.split(";")[1].split("=")[1])),typeof r.download>"u"&&r.setAttribute("target","_blank"),document.body.appendChild(r),r.click(),document.body.removeChild(r),window.URL.revokeObjectURL(s)}).catch(a=>{n.error("训练营名单模版文件下载失败:"+a.message)})},R=async(a,t)=>{let e=null;const s=l();return await c.get(`${i}/getPersonnelList`,{params:{pageSize:a,pageNumber:t},headers:{"Session-Id":window.localStorage.getItem(void 0),Key:s.aesKey_RSA,Iv:s.aesIv_RSA,Encrypt:"0"}}).then(r=>{if(r.state===200){e=r.data;const o=d.getAESUtils().decrypt(e.tableData);e.tableData=JSON.parse(o)}else n.error(r.message)}).catch(r=>{n.error("获取人员名单失败:"+r.message)}),e},D=async a=>{let t=!1;return await c.delete(i+"/deleteUser",{params:{uid:a},headers:{"Session-Id":window.localStorage.getItem(void 0),Encrypt:"0"}}).then(e=>{e.state===200?(n.success("人员删除成功"),t=!0):n.warning(e.message)}).catch(e=>{n.error("人员删除失败"+e.message)}),t},A=async a=>{let t=!1;return await c.put(i+"/updateUserInfo",a,{headers:{"Session-Id":window.localStorage.getItem(void 0),Encrypt:"0"}}).then(e=>{e.state===200?(n.success("用户信息更新成功"),t=!0):n.warning(e.message)}).catch(e=>{n.error("用户信息更新失败"+e.message)}),t};export{S as a,y as b,A as c,R as d,b as e,D as f,m as g,I as h,f as i,h as j,w as u};
