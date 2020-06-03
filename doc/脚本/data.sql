插入订单数的存储过程：
delimiter //
drop procedure if exists looppc;
create procedure looppc()
begin 
declare i int;
set i = 1;

repeat 
    INSERT INTO swipe(order_id,asin,price,receive,rebate,commission,product_name,description,order_date,rebate_date) VALUES('88888888'+i,'88888888'+i,88.88+i,50+i,138.88+i+i,88.88+i,'吸尘器','佣金为50人民币得吸尘器',SYSDATE(),SYSDATE());
    set i = i + 1;
until i >= 88

end repeat;


end //

---- 调用
call looppc()