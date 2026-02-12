/**
 * 后端统一响应结构（与你的 ApiResponse 对齐）
 */
export interface ApiResponse<T = any> {
    code: number
    message: string
    data: T
}