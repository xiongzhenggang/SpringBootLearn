package com.xzg.cn.java9Test.javadesage.data_bus.members;

import com.xzg.cn.java9Test.javadesage.data_bus.DataType;
import com.xzg.cn.java9Test.javadesage.data_bus.data.MessageData;
import com.xzg.cn.java9Test.javadesage.data_bus.data.StartingData;
import com.xzg.cn.java9Test.javadesage.data_bus.data.StoppingData;
import com.xzg.cn.java9Test.javadesage.data_bus.Member;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class StatusMember implements Member {

    private static final Logger LOGGER = Logger.getLogger(StatusMember.class.getName());

    private final int id;

    private LocalDateTime started;

    private LocalDateTime stopped;

    public StatusMember(int id) {
        this.id = id;
    }

    @Override
    public void accept(final DataType data) {
        if (data instanceof StartingData) {
            handleEvent((StartingData) data);
        } else if (data instanceof StoppingData) {
            handleEvent((StoppingData) data);
        }
    }

    private void handleEvent(StartingData data) {
        started = data.getWhen();
        LOGGER.info(String.format("Receiver #%d sees application started at %s", id, started));
    }

    private void handleEvent(StoppingData data) {
        stopped = data.getWhen();
        LOGGER.info(String.format("Receiver #%d sees application stopping at %s", id, stopped));
        LOGGER.info(String.format("Receiver #%d sending goodbye message", id));
        data.getDataBus().publish(MessageData.of(String.format("Goodbye cruel world from #%d!", id)));
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public LocalDateTime getStopped() {
        return stopped;
    }
}