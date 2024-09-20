import router from '@/router'
import axios, {
  AxiosError,
  type AxiosInstance,
  type AxiosRequestConfig,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

// 响应体
interface Response<T> {
  state: number
  message: string
  date: string
  data?: T
}

// 请求地址前缀
const URL: string = '/api'
// 请求超时时间
const TIMEOUT: number = 60000

//axios配置
const config: AxiosRequestConfig = {
  // 默认地址
  baseURL: URL,
  // 设置超时时间
  timeout: TIMEOUT,
  // 跨域时候允许携带凭证
  withCredentials: true
}

class RequestHttp {
  // axios 实例
  service: AxiosInstance

  public constructor(config: AxiosRequestConfig) {
    // 实例化axios
    this.service = axios.create(config)
    /**
     * 请求拦截器
     * 客户端发送请求 -> [请求拦截器] -> 服务器
     */
    this.service.interceptors.request.use(
      (request: InternalAxiosRequestConfig) => {
        console.debug('请求拦截器:', request)
        return request
      },
      (err: AxiosError) => {
        return Promise.reject(err)
      }
    )

    /**
     * 响应拦截器
     * 服务器换返回信息 -> [拦截统一处理] -> 客户端JS获取到信息
     */
    this.service.interceptors.response.use(
      (res: AxiosResponse) => {
        console.debug('响应拦截器:', res)
        return res.data
      },
      (err: AxiosError) => {
        switch (err.response!.status) {
          case 400:
            ElMessage.error('请求错误')
            break
          case 401:
            ElMessageBox.alert('身份验证过期请重新登陆', '警告', {
              confirmButtonText: '确认',
              showClose: false,
              callback: () => {
                window.localStorage.removeItem(import.meta.env.VITE_SESSION_ID)
                router.push('/').then((res) => console.log(res))
              }
            }).then(() => {})
            break
          case 403:
            ElMessage.error('拒绝访问')
            break
          case 404:
            ElMessage.error('请求错误')
            break
          case 408:
            ElMessage.error('请求超时')
            break
          case 500:
            ElMessage.error('服务器错误')
            break
          case 501:
            ElMessage.error('服务未实现')
            break
          case 502:
            ElMessage.error('网络错误')
            break
          case 503:
            ElMessage.error('服务不可用')
            break
          case 504:
            ElMessage.error('网络超时')
            break
          case 505:
            ElMessage.error('HTTP版本不受支持')
            break
          default:
            ElMessage.error(`连接出错(${err.message})!`)
        }
        return Promise.reject(err)
      }
    )
  }

  // 常用方法封装
  public get<T>(url: string, config?: object): Promise<Response<T>> {
    return this.service.get(url, config)
  }

  public post<T>(url: string, params?: object, config?: object): Promise<Response<T>> {
    return this.service.post(url, params, config)
  }

  public put<T>(url: string, params?: object, config?: object): Promise<Response<T>> {
    return this.service.put(url, params, config)
  }

  public delete<T>(url: string, config?: object): Promise<Response<T>> {
    return this.service.delete(url, config)
  }

  public patch<t>(url: string, params?: object, config?: object): Promise<Response<t>> {
    return this.service.patch(url, params, config)
  }
}

const server = new RequestHttp(config)

export * from '@/server/module/everyman'
export * from '@/server/module/personInCharge'
export * from '@/server/module/public'
export * from '@/server/module/user'

export default server
