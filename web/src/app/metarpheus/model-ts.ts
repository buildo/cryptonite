import * as t from 'io-ts'
export interface Unit {}
export const Unit = t.interface({}, 'Unit');
export type Currency = 
  | 'Bitcoin'
  | 'BitcoinCash'
  | 'Litecoin'
  | 'Ethereum'
  | 'Ripple'
  | 'Cardano'
  | 'NEM'
  | 'Stellar'
  | 'IOTA'
  | 'TRON'
  | 'Dash'
  | 'NEO'
  | 'Monero'
  | 'EOS'
  | 'ICON'
  | 'Qtum'
  | 'BitcoinGold'
  | 'Lisk'
  | 'RaiBlocks'
  | 'EthereumClassic'
  | 'Euro'
  | 'Dollar'
  | 'Pound'

export const Currency = t.keyof({
  Bitcoin: true,
  BitcoinCash: true,
  Litecoin: true,
  Ethereum: true,
  Ripple: true,
  Cardano: true,
  NEM: true,
  Stellar: true,
  IOTA: true,
  TRON: true,
  Dash: true,
  NEO: true,
  Monero: true,
  EOS: true,
  ICON: true,
  Qtum: true,
  BitcoinGold: true,
  Lisk: true,
  RaiBlocks: true,
  EthereumClassic: true,
  Euro: true,
  Dollar: true,
  Pound: true
}, 'Currency')

export interface Amount {
  value: number,
  currency: Currency
}

export const Amount = t.interface({
  value: t.number,
  currency: Currency
}, 'Amount')

export type Exchange = 
  | 'GDAX'
  | 'Kraken'
  | 'Bitfinex'
  | 'Bitstamp'

export const Exchange = t.keyof({
  GDAX: true,
  Kraken: true,
  Bitfinex: true,
  Bitstamp: true
}, 'Exchange')

export interface Product {
  base: Currency,
  quote: Currency
}

export const Product = t.interface({
  base: Currency,
  quote: Currency
}, 'Product')

export interface Book {
  exchange: Exchange,
  product: Product,
  bid: Amount,
  ask: Amount,
  value: Amount
}

export const Book = t.interface({
  exchange: Exchange,
  product: Product,
  bid: Amount,
  ask: Amount,
  value: Amount
}, 'Book')

export type Column = 
  | 'Exchange'
  | 'Product'
  | 'Bid'
  | 'Ask'
  | 'Value'

export const Column = t.keyof({
  Exchange: true,
  Product: true,
  Bid: true,
  Ask: true,
  Value: true
}, 'Column')