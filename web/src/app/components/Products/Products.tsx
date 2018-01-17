import * as React from 'react';
import { Book, Product, Amount, Currency } from 'model';
import { Tablo, Column, Cell, Header } from 'Basic/Tablo';

const currencyMap: { [k in Currency]: string } = {
  Bitcoin: 'BTC',
  BitcoinCash: 'BCH',
  Litecoin: 'LTC',
  Ethereum: 'ETH',
  Ripple: 'XRP',
  Cardano: 'ADA',
  NEM: 'XEM',
  Stellar: 'XLM',
  IOTA: 'IOT',
  TRON: 'TRX',
  Dash: 'DSH',
  NEO: 'NEO',
  Monero: 'XMR',
  EOS: 'EOS',
  ICON: 'ICX',
  Qtum: 'QTM',
  BitcoinGold: 'BTG',
  Lisk: 'LSK',
  RaiBlocks: 'XRB',
  EthereumClassic: 'ETC',
  Euro: 'EUR',
  Dollar: 'USD',
  Pound: 'GBP'
}

type Props = {
  books: Array<Book>
};
export default class Products extends React.PureComponent<Props> {
  render() {
    const data = this.props.books;
    return (
      <Tablo
       data={data}
      >
        <Column name='exchange' width={200}>
          <Header>Exchange</Header>
          <Cell>{ (exchange: string) => exchange}</Cell>
        </Column>

        <Column name='product' width={200}>
          <Header>Products</Header>
          <Cell>{ (product: Product) => `${currencyMap[product.base]}-${currencyMap[product.quote]}` }</Cell>
        </Column>

        <Column name='bid' width={200}>
          <Header>Bid</Header>
          <Cell>{ (amount: Amount) => `${amount.value}`}</Cell>
        </Column>

        <Column name='ask' width={200}>
          <Header>Ask</Header>
          <Cell>{ (amount: Amount) => `${amount.value}`}</Cell>
        </Column>

        <Column name='value' width={200}>
          <Header>Value</Header>
          <Cell>{ (amount: Amount) => `${amount.value} ${currencyMap[amount.currency]}`}</Cell>
        </Column>
      </Tablo>
    )
  }
}
