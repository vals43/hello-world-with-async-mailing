CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE course (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_instant TIMESTAMP WITH TIME ZONE NOT NULL,
    end_instant TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE user_course (
    user_id UUID NOT NULL REFERENCES app_user(id),
    course_id UUID NOT NULL REFERENCES course(id),
    PRIMARY KEY (user_id, course_id)
);

CREATE INDEX idx_user_course_user_id ON user_course(user_id);
CREATE INDEX idx_user_course_course_id ON user_course(course_id);
