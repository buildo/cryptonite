import * as React from 'react';
import { Book, Product, Amount, Currency, Column as ColumnType } from 'model';
import { Tablo, Column, Cell, Header } from 'Basic/Tablo';
import { LoadingSpinner } from 'Basic';

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

const columnsToSortByMap: { [k in keyof Book]: ColumnType } = {
  exchange: 'Exchange',
  product: 'Product',
  bid: 'Bid',
  ask: 'Ask',
  value: 'Value'
}

const sortByToColumnsMap: { [k in ColumnType]: keyof Book } = {
  Exchange: 'exchange',
  Product: 'product',
  Bid: 'bid',
  Ask: 'ask',
  Value: 'value'
}

type SortBy = ColumnType;
type SortDir  = 'asc'| 'desc';

type Props = {
  books: Array<Book>,
  sortBy: SortBy,
  sortDir: SortDir,
  onSortChange: (sort: { sortBy: SortBy, sortDir: SortDir }) => void
};
export default class Products extends React.PureComponent<Props> {

  onSortChange = (x: {sortBy: keyof Book, sortDir: SortDir}) => {
    this.props.onSortChange( { sortBy: columnsToSortByMap[x.sortBy], sortDir: x.sortDir })
  }

  render() {
    const { sortBy, sortDir, books: data } = this.props;
    return data ? (
      <Tablo
       data={data}
       sortBy={sortByToColumnsMap[sortBy]}
       sortDir={sortDir}
       onSortChange={this.onSortChange}
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
    ) : <LoadingSpinner />
  }
}
