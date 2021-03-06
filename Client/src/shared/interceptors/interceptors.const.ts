import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {APIInterceptor} from "./api-interceptor";
import {ErrorInterceptor} from "./error.interceptor";

export const interceptors = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: APIInterceptor,
    multi: true,
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptor,
    multi: true,
  }
]
