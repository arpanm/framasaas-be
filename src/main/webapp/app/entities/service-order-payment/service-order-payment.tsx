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

import { getEntities, reset } from './service-order-payment.reducer';

export const ServiceOrderPayment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderPaymentList = useAppSelector(state => state.serviceOrderPayment.entities);
  const loading = useAppSelector(state => state.serviceOrderPayment.loading);
  const links = useAppSelector(state => state.serviceOrderPayment.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderPayment.updateSuccess);

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
      <h2 id="service-order-payment-heading" data-cy="ServiceOrderPaymentHeading">
        <Translate contentKey="framasaasApp.serviceOrderPayment.home.title">Service Order Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderPayment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-payment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderPayment.home.createLabel">Create new Service Order Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderPaymentList ? serviceOrderPaymentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderPaymentList && serviceOrderPaymentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('paymentLink')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.paymentLink">Payment Link</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentLink')} />
                  </th>
                  <th className="hand" onClick={sort('paymentStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.paymentStatus">Payment Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('mop')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.mop">Mop</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('mop')} />
                  </th>
                  <th className="hand" onClick={sort('pgTxnId')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnId">Pg Txn Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pgTxnId')} />
                  </th>
                  <th className="hand" onClick={sort('pgTxnResponse')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnResponse">Pg Txn Response</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pgTxnResponse')} />
                  </th>
                  <th className="hand" onClick={sort('pgTxnResponseTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnResponseTime">Pg Txn Response Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pgTxnResponseTime')} />
                  </th>
                  <th className="hand" onClick={sort('paymentInitiatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.paymentInitiatedBy">Payment Initiated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentInitiatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderPayment.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderPaymentList.map((serviceOrderPayment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/service-order-payment/${serviceOrderPayment.id}`} color="link" size="sm">
                        {serviceOrderPayment.id}
                      </Button>
                    </td>
                    <td>{serviceOrderPayment.paymentLink}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.PaymentStatus.${serviceOrderPayment.paymentStatus}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.ModeOfPayment.${serviceOrderPayment.mop}`} />
                    </td>
                    <td>{serviceOrderPayment.pgTxnId}</td>
                    <td>{serviceOrderPayment.pgTxnResponse}</td>
                    <td>
                      {serviceOrderPayment.pgTxnResponseTime ? (
                        <TextFormat type="date" value={serviceOrderPayment.pgTxnResponseTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderPayment.paymentInitiatedBy}</td>
                    <td>{serviceOrderPayment.isActive ? 'true' : 'false'}</td>
                    <td>{serviceOrderPayment.createddBy}</td>
                    <td>
                      {serviceOrderPayment.createdTime ? (
                        <TextFormat type="date" value={serviceOrderPayment.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderPayment.updatedBy}</td>
                    <td>
                      {serviceOrderPayment.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderPayment.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-payment/${serviceOrderPayment.id}`}
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
                          to={`/service-order-payment/${serviceOrderPayment.id}/edit`}
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
                          onClick={() => (window.location.href = `/service-order-payment/${serviceOrderPayment.id}/delete`)}
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
                <Translate contentKey="framasaasApp.serviceOrderPayment.home.notFound">No Service Order Payments found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderPayment;
