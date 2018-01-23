import * as React from 'react';
import { Book, Product, Amount, Currency, Column as ColumnType, Exchange } from 'model';
import { Tablo, Column, Cell, Header } from 'Basic/Tablo';
import { Dropdown, LoadingSpinner, FlexView } from 'Basic';

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

const exchangesMap: { [k in Exchange]: { value: Exchange, label: string } } = {
  GDAX: { value: 'GDAX', label: 'GDAX' },
  Kraken: { value: 'Kraken', label: 'Kraken' },
  Bitfinex: { value: 'Bitfinex', label: 'Bitfinex' },
  Bitstamp: { value: 'Bitstamp', label: 'Bitstamp' }
}

const currencyOptionMap: { [k in Currency]: { value: Currency, label: string } } = {
  Bitcoin: { value: 'Bitcoin', label: 'Bitcoin' },
  BitcoinCash: { value: 'BitcoinCash', label: 'Bitcoin Cash' },
  Litecoin: { value: 'Litecoin', label: 'Litecoin' },
  Ethereum: { value: 'Ethereum', label: 'Ethereum' },
  Ripple: { value: 'Ripple', label: 'Ripple' },
  Cardano: { value: 'Cardano', label: 'Cardano' },
  NEM: { value: 'NEM', label: 'NEM' },
  Stellar: { value: 'Stellar', label: 'Stellar' },
  IOTA: { value: 'IOTA', label: 'IOTA' },
  TRON: { value: 'TRON', label: 'TRON' },
  Dash: { value: 'Dash', label: 'Dash' },
  NEO: { value: 'NEO', label: 'NEO' },
  Monero: { value: 'Monero', label: 'Monero' },
  EOS: { value: 'EOS', label: 'EOS' },
  ICON: { value: 'ICON', label: 'ICON' },
  Qtum: { value: 'Qtum', label: 'Qtum' },
  BitcoinGold: { value: 'BitcoinGold', label: 'Bitcoin Gold' },
  Lisk: { value: 'Lisk', label: 'Lisk' },
  RaiBlocks: { value: 'RaiBlocks', label: 'RaiBlocks' },
  EthereumClassic: { value: 'EthereumClassic', label: 'Ethereum Classic' },
  Euro: { value: 'Euro', label: 'Euro' },
  Dollar: { value: 'Dollar', label: 'Dollar' },
  Pound: { value: 'Pound', label: 'Pound' },
}

type SortBy = ColumnType;
type SortDir  = 'asc'| 'desc';

type Props = {
  books: Array<Book>,
  exchanges: Array<string>,
  base: Currency | undefined,
  quote: Currency | undefined,
  sortBy: SortBy,
  sortDir: SortDir,
  onSortChange: (sort: { sortBy: SortBy, sortDir: SortDir }) => void,
  onExchangeChange: ( exchanges: Array<Exchange> ) => void,
  onBaseChange: ( base: Currency | undefined ) => void,
  onQuoteChange: ( quote: Currency | undefined ) => void
};
export default class Products extends React.PureComponent<Props> {

  onSortChange = (x: {sortBy: keyof Book, sortDir: SortDir}) => {
    this.props.onSortChange( { sortBy: columnsToSortByMap[x.sortBy], sortDir: x.sortDir })
  }

  onExchangeChange = (value: string) => this.props.onExchangeChange(value.split(",") as Array<Exchange>)

  render() {
    const { exchanges, base, quote, sortBy, sortDir, books: data } = this.props;
    return data ? (
      <FlexView column grow>
        <FlexView hAlignContent='center'>
          <FlexView basis='50%' grow>
            <Dropdown
              value={Array.prototype.join.call(exchanges, ',')}
              onChange={this.onExchangeChange}
              placeholder={'Select some exchanges'}
              multi
              clearable
              options={ Object.keys(exchangesMap).map(key => exchangesMap[key]) }
            />
          </FlexView>
          <FlexView basis='25%' grow>
            <Dropdown
              value={base}
              onChange={this.props.onBaseChange}
              placeholder='Select a base currency'
              clearable
              options={ Object.keys(currencyOptionMap).map(key => currencyOptionMap[key]) }
            />
          </FlexView>
          <FlexView basis='25%' grow>
            <Dropdown
              value={quote}
              onChange={this.props.onQuoteChange}
              placeholder='Select a quote currency'
              clearable
              options={ Object.keys(currencyOptionMap).map(key => currencyOptionMap[key]) }
            />
          </FlexView>
        </FlexView>
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
      </FlexView>
    ) : <LoadingSpinner />
  }
}
