import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './product-price-history.reducer';

export const ProductPriceHistory = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const productPriceHistoryList = useAppSelector(state => state.productPriceHistory.entities);
  const loading = useAppSelector(state => state.productPriceHistory.loading);
  const links = useAppSelector(state => state.productPriceHistory.links);
  const updateSuccess = useAppSelector(state => state.productPriceHistory.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="product-price-history-heading" data-cy="ProductPriceHistoryHeading">
        <Translate contentKey="framasaasApp.productPriceHistory.home.title">Product Price Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.productPriceHistory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/product-price-history/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.productPriceHistory.home.createLabel">Create new Product Price History</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={productPriceHistoryList ? productPriceHistoryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {productPriceHistoryList && productPriceHistoryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.price">Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.tax">Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('tax')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommission')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.franchiseCommission">Franchise Commission</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommission')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseTax')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.franchiseTax">Franchise Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseTax')} />
                  </th>
                  <th className="hand" onClick={sort('updateDescription')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.updateDescription">Update Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updateDescription')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.productPriceHistory.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.productPriceHistory.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {productPriceHistoryList.map((productPriceHistory, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/product-price-history/${productPriceHistory.id}`} color="link" size="sm">
                        {productPriceHistory.id}
                      </Button>
                    </td>
                    <td>{productPriceHistory.price}</td>
                    <td>{productPriceHistory.tax}</td>
                    <td>{productPriceHistory.franchiseCommission}</td>
                    <td>{productPriceHistory.franchiseTax}</td>
                    <td>{productPriceHistory.updateDescription}</td>
                    <td>{productPriceHistory.updatedBy}</td>
                    <td>
                      {productPriceHistory.updatedTime ? (
                        <TextFormat type="date" value={productPriceHistory.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {productPriceHistory.product ? (
                        <Link to={`/product/${productPriceHistory.product.id}`}>{productPriceHistory.product.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/product-price-history/${productPriceHistory.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/product-price-history/${productPriceHistory.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/product-price-history/${productPriceHistory.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="framasaasApp.productPriceHistory.home.notFound">No Product Price Histories found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ProductPriceHistory;
