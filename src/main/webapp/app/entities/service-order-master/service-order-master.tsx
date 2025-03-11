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

import { getEntities, reset } from './service-order-master.reducer';

export const ServiceOrderMaster = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderMasterList = useAppSelector(state => state.serviceOrderMaster.entities);
  const loading = useAppSelector(state => state.serviceOrderMaster.loading);
  const links = useAppSelector(state => state.serviceOrderMaster.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderMaster.updateSuccess);

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
      <h2 id="service-order-master-heading" data-cy="ServiceOrderMasterHeading">
        <Translate contentKey="framasaasApp.serviceOrderMaster.home.title">Service Order Masters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderMaster.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-master/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderMaster.home.createLabel">Create new Service Order Master</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderMasterList ? serviceOrderMasterList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderMasterList && serviceOrderMasterList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('serviceOrderType')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.serviceOrderType">Service Order Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrderType')} />
                  </th>
                  <th className="hand" onClick={sort('slaInHours')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.slaInHours">Sla In Hours</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('slaInHours')} />
                  </th>
                  <th className="hand" onClick={sort('charge')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.charge">Charge</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('charge')} />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.tax">Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('tax')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommissionWithinSla')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseCommissionWithinSla">
                      Franchise Commission Within Sla
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommissionWithinSla')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseTaxWithinSlaTax')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseTaxWithinSlaTax">
                      Franchise Tax Within Sla Tax
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseTaxWithinSlaTax')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommissionOutsideSla')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseCommissionOutsideSla">
                      Franchise Commission Outside Sla
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommissionOutsideSla')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseTaxOutsideSlaTax')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseTaxOutsideSlaTax">
                      Franchise Tax Outside Sla Tax
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseTaxOutsideSlaTax')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderMaster.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderMasterList.map((serviceOrderMaster, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/service-order-master/${serviceOrderMaster.id}`} color="link" size="sm">
                        {serviceOrderMaster.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.ServiceOrderType.${serviceOrderMaster.serviceOrderType}`} />
                    </td>
                    <td>{serviceOrderMaster.slaInHours}</td>
                    <td>{serviceOrderMaster.charge}</td>
                    <td>{serviceOrderMaster.tax}</td>
                    <td>{serviceOrderMaster.franchiseCommissionWithinSla}</td>
                    <td>{serviceOrderMaster.franchiseTaxWithinSlaTax}</td>
                    <td>{serviceOrderMaster.franchiseCommissionOutsideSla}</td>
                    <td>{serviceOrderMaster.franchiseTaxOutsideSlaTax}</td>
                    <td>{serviceOrderMaster.isActive ? 'true' : 'false'}</td>
                    <td>{serviceOrderMaster.createddBy}</td>
                    <td>
                      {serviceOrderMaster.createdTime ? (
                        <TextFormat type="date" value={serviceOrderMaster.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderMaster.updatedBy}</td>
                    <td>
                      {serviceOrderMaster.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderMaster.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderMaster.product ? (
                        <Link to={`/product/${serviceOrderMaster.product.id}`}>{serviceOrderMaster.product.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-master/${serviceOrderMaster.id}`}
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
                          to={`/service-order-master/${serviceOrderMaster.id}/edit`}
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
                          onClick={() => (window.location.href = `/service-order-master/${serviceOrderMaster.id}/delete`)}
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
                <Translate contentKey="framasaasApp.serviceOrderMaster.home.notFound">No Service Order Masters found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderMaster;
