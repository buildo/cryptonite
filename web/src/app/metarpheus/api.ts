

// DO NOT EDIT MANUALLY - metarpheus-generated
import axios from 'axios'
import * as t from 'io-ts'
import * as m from './model-ts'

interface RouteConfig {
  apiEndpoint: string,
  timeout: number,
  unwrapApiResponse: (resp: any) => any
}

import { failure } from 'io-ts/lib/PathReporter'
export function unsafeValidate<S, A>(value: any, type: t.Type<S, A>): A {
  if (process.env.NODE_ENV !== 'production') {
    return t.validate(value, type).fold(errors => {
      throw new Error(failure(errors).join('\n'))
    }, t.identity)
  }
  return value as A
}

export default function getRoutes(config: RouteConfig) {
  return {
    portfolioController_read: function ({  }: {  }): Promise<Array<m.Amount>> {
      return axios({
        method: 'get',
        url: `${config.apiEndpoint}/portfolio/read`,
        params: {

        },
        data: {

        },
        headers: {
          'Content-Type': 'application/json',
          'Pragma': 'no-cache',
          'Cache-Control': 'no-cache, no-store'
        },
        timeout: config.timeout
      }).then(res => unsafeValidate(config.unwrapApiResponse(res.data), t.array(m.Amount))) as any
    },

    portfolioController_save: function ({ amounts }: { amounts: Array<m.Amount> }): Promise<m.Unit> {
      return axios({
        method: 'post',
        url: `${config.apiEndpoint}/portfolio/save`,
        params: {

        },
        data: {
          amounts
        },
        headers: {
          'Content-Type': 'application/json'
        },
        timeout: config.timeout
      }).then(res => unsafeValidate(config.unwrapApiResponse(res.data), m.Unit)) as any
    },

    productsController_read: function ({ exchanges, base, quote, sortBy, ascending }: { exchanges: Array<m.Exchange>, base: 
  | m.Currency
  | undefined, quote: 
  | m.Currency
  | undefined, sortBy: m.Column, ascending: boolean }): Promise<Array<m.Book>> {
      return axios({
        method: 'get',
        url: `${config.apiEndpoint}/products/read`,
        params: {
          exchanges,
          base,
          quote,
          sortBy,
          ascending
        },
        data: {

        },
        headers: {
          'Content-Type': 'application/json',
          'Pragma': 'no-cache',
          'Cache-Control': 'no-cache, no-store'
        },
        timeout: config.timeout
      }).then(res => unsafeValidate(config.unwrapApiResponse(res.data), t.array(m.Book))) as any
    }
  }
}
