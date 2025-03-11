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

import { getEntities, reset } from './service-order-assignment.reducer';

export const ServiceOrderAssignment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderAssignmentList = useAppSelector(state => state.serviceOrderAssignment.entities);
  const loading = useAppSelector(state => state.serviceOrderAssignment.loading);
  const links = useAppSelector(state => state.serviceOrderAssignment.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderAssignment.updateSuccess);

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
      <h2 id="service-order-assignment-heading" data-cy="ServiceOrderAssignmentHeading">
        <Translate contentKey="framasaasApp.serviceOrderAssignment.home.title">Service Order Assignments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderAssignment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-assignment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderAssignment.home.createLabel">Create new Service Order Assignment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderAssignmentList ? serviceOrderAssignmentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderAssignmentList && serviceOrderAssignmentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('serviceOrderAssignmentStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.serviceOrderAssignmentStatus">
                      Service Order Assignment Status
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrderAssignmentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('nps')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.nps">Nps</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nps')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('assignedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.assignedTime">Assigned Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('assignedTime')} />
                  </th>
                  <th className="hand" onClick={sort('movedBackTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.movedBackTime">Moved Back Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('movedBackTime')} />
                  </th>
                  <th className="hand" onClick={sort('visitTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.visitTime">Visit Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('visitTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareOrderTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.spareOrderTime">Spare Order Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareOrderTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareDeliveryTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.spareDeliveryTime">Spare Delivery Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareDeliveryTime')} />
                  </th>
                  <th className="hand" onClick={sort('completedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.completedTime">Completed Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('completedTime')} />
                  </th>
                  <th className="hand" onClick={sort('completionOTP')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.completionOTP">Completion OTP</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('completionOTP')} />
                  </th>
                  <th className="hand" onClick={sort('cancellationOTP')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.cancellationOTP">Cancellation OTP</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('cancellationOTP')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommision')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseCommision">Franchise Commision</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommision')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommisionTax')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseCommisionTax">Franchise Commision Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommisionTax')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseInvoicePath')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseInvoicePath">Franchise Invoice Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseInvoicePath')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.serviceOrder">Service Order</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderAssignment.franchise">Franchise</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderAssignmentList.map((serviceOrderAssignment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/service-order-assignment/${serviceOrderAssignment.id}`} color="link" size="sm">
                        {serviceOrderAssignment.id}
                      </Button>
                    </td>
                    <td>
                      <Translate
                        contentKey={`framasaasApp.ServiceOrderAssignmentStatus.${serviceOrderAssignment.serviceOrderAssignmentStatus}`}
                      />
                    </td>
                    <td>{serviceOrderAssignment.nps}</td>
                    <td>{serviceOrderAssignment.isActive ? 'true' : 'false'}</td>
                    <td>
                      {serviceOrderAssignment.assignedTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.assignedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.movedBackTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.movedBackTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.visitTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.visitTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.spareOrderTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.spareOrderTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.spareDeliveryTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.spareDeliveryTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.completedTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.completedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderAssignment.completionOTP}</td>
                    <td>{serviceOrderAssignment.cancellationOTP}</td>
                    <td>{serviceOrderAssignment.franchiseCommision}</td>
                    <td>{serviceOrderAssignment.franchiseCommisionTax}</td>
                    <td>{serviceOrderAssignment.franchiseInvoicePath}</td>
                    <td>{serviceOrderAssignment.createddBy}</td>
                    <td>
                      {serviceOrderAssignment.createdTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderAssignment.updatedBy}</td>
                    <td>
                      {serviceOrderAssignment.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderAssignment.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderAssignment.serviceOrder ? (
                        <Link to={`/service-order/${serviceOrderAssignment.serviceOrder.id}`}>
                          {serviceOrderAssignment.serviceOrder.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {serviceOrderAssignment.franchise ? (
                        <Link to={`/franchise/${serviceOrderAssignment.franchise.id}`}>{serviceOrderAssignment.franchise.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-assignment/${serviceOrderAssignment.id}`}
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
                          to={`/service-order-assignment/${serviceOrderAssignment.id}/edit`}
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
                          onClick={() => (window.location.href = `/service-order-assignment/${serviceOrderAssignment.id}/delete`)}
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
                <Translate contentKey="framasaasApp.serviceOrderAssignment.home.notFound">No Service Order Assignments found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderAssignment;
