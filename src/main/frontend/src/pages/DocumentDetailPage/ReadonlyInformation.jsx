import React from 'react';
import { Card, CardContent, Grid, TextField, Typography } from '@mui/material';

const ReadonlyInformation = ({ data }) => {
    return (
        <Card>
            <CardContent>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                    기본 정보
                </Typography>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6}><TextField fullWidth label="작성일자" value={data.documentDate} InputProps={{ readOnly: true }} /></Grid>
                    <Grid item xs={12} sm={6}><TextField fullWidth label="작성자" value={data.userName} InputProps={{ readOnly: true }} /></Grid>
                    <Grid item xs={12} sm={6}><TextField fullWidth label="부서" value={data.userBuseo} InputProps={{ readOnly: true }} /></Grid>
                    <Grid item xs={12} sm={6}><TextField fullWidth label="제목" value={data.documentTitle} InputProps={{ readOnly: true }} /></Grid>
                    <Grid item xs={12} sm={6}><TextField fullWidth label="공개여부" value={data.documentOpen} InputProps={{ readOnly: true }} /></Grid>
                </Grid>
            </CardContent>
        </Card>
    );
};

export default ReadonlyInformation;
