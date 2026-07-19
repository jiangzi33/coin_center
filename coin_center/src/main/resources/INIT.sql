CREATE database coin_center;
use coin_center;
CREATE TABLE coin_center (
                             id int PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                             code VARCHAR(50) NOT NULL COMMENT '金币编号',
                             price double NOT NULL COMMENT '价格',
                             store INT NOT NULL COMMENT '库存',
                             last_operation_time DATETIME COMMENT '最后操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment "金币表";


CREATE TABLE coin_record (
                             id INT NOT NULL AUTO_INCREMENT,
                             code VARCHAR(100) NOT NULL,
                             out_biz_no VARCHAR(100) NOT NULL,
                             prize_time DATETIME NOT NULL,
                             amount INT NOT NULL,

                             PRIMARY KEY (id),
                             UNIQUE KEY uk_out_biz_no (out_biz_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;