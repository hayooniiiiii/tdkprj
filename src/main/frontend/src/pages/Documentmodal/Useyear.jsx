import React, {forwardRef, useEffect, useImperativeHandle, useState} from 'react';
import {
    Card, CardContent, Typography,
    Table, TableHead, TableRow, TableCell, TableBody
} from '@mui/material';
import axios from "axios";

const Useyear = forwardRef((props,ref) => {
    const [leavedata, setLeavedata] = useState(null);

    useImperativeHandle(ref, () => ({
        getData: () => leavedata  //

    }));
    useEffect(() => {
        const fetchLeave = async () => {
            try {
                const res = await axios.get('http://localhost:8080/document/leave', {
                    withCredentials: true,
                });
                setLeavedata(res.data);
                console.log("data",res.data);
            } catch (error) {
                console.error('연차 기록 가져오기 실패:', error);
            }
        };

        fetchLeave();
    }, []);

    return (
        <Card sx={{ borderRadius: 2, width: '100%' }}>
            <CardContent sx={{ p: 3 }}>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                    연차 사용 현황
                </Typography>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">기준년도</TableCell>
                            <TableCell align="center">휴가유형</TableCell>
                            <TableCell align="center">이월연차</TableCell>
                            <TableCell align="center">당해월차</TableCell>
                            <TableCell align="center">당해연차</TableCell>
                            <TableCell align="center">사용일수</TableCell>
                            <TableCell align="center">잔여일수</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell align="center">{leavedata?.year ?? '-'}</TableCell>
                            <TableCell align="center">연차휴가</TableCell>
                            <TableCell align="center">{leavedata?.monthAccrued ?? 0}</TableCell>
                            <TableCell align="center">{leavedata?.monthUsed ?? 0}</TableCell>
                            <TableCell align="center">{leavedata?.monthlyBalance ?? 0}</TableCell>
                            <TableCell align="center">
                                {leavedata?.usedDays !== undefined
                                    ? Number(leavedata.usedDays).toFixed(2)
                                    : '0.00'}
                            </TableCell>
                            <TableCell align="center">
                                {leavedata?.usedDays !== undefined
                                    ? (16 - Number(leavedata.usedDays)).toFixed(2)
                                    : '0.00'}
                            </TableCell>
                        </TableRow>
                    </TableBody>


                </Table>
            </CardContent>
        </Card>
    );
});

export default Useyear;
